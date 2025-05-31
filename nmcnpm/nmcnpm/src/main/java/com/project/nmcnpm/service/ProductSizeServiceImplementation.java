package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.ProductSizeRepository;
import com.project.nmcnpm.dao.ProductVariantRepository; // Cần để tìm ProductVariant entity
import com.project.nmcnpm.dao.ProductRepository; // Cần để cập nhật stock/price của sản phẩm
import com.project.nmcnpm.dto.ProductSizeDTO;
import com.project.nmcnpm.dto.ProductSizeResponseDTO;
import com.project.nmcnpm.entity.Product;
import com.project.nmcnpm.entity.ProductSize;
import com.project.nmcnpm.entity.ProductVariant;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal; // Vẫn cần nếu ProductSize.price là BigDecimal
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSizeServiceImplementation implements ProductSizeService {

    private final ProductSizeRepository productSizeRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository; // Để cập nhật stock/price của sản phẩm cha

    public ProductSizeServiceImplementation(ProductSizeRepository productSizeRepository,
                                            ProductVariantRepository productVariantRepository,
                                            ProductRepository productRepository) {
        this.productSizeRepository = productSizeRepository;
        this.productVariantRepository = productVariantRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductSize createProductSize(ProductSizeDTO productSizeDTO) {
        ProductVariant productVariant = productVariantRepository.findById(productSizeDTO.getProductVariantId())
                .orElseThrow(() -> new EntityNotFoundException("Product Variant not found with id: " + productSizeDTO.getProductVariantId()));

        ProductSize productSize = new ProductSize();
        productSize.setProductVariant(productVariant);
        productSize.setSize(productSizeDTO.getSize());
        productSize.setPrice(productSizeDTO.getPrice());
        productSize.setQuantity(productSizeDTO.getQuantity());

        productVariant.addProductSize(productSize); // Thêm vào bộ sưu tập của biến thể

        ProductSize savedSize = productSizeRepository.save(productSize);

        // Cập nhật stock và khoảng giá của sản phẩm
        updateProductStockAndPrice(productVariant.getProduct());

        return savedSize;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductSizeResponseDTO getProductSizeById(Integer sizeId) {
        ProductSize productSize = productSizeRepository.findById(sizeId)
                .orElseThrow(() -> new EntityNotFoundException("Product Size not found with id: " + sizeId));
        return new ProductSizeResponseDTO(productSize);
    }

    @Override
    @Transactional
    public ProductSize updateProductSize(Integer sizeId, ProductSizeDTO productSizeDTO) {
        ProductSize existingSize = productSizeRepository.findById(sizeId)
                .orElseThrow(() -> new EntityNotFoundException("Product Size not found with id: " + sizeId));

        Product originalProduct = existingSize.getProductVariant().getProduct(); // Giữ sản phẩm gốc

        if (productSizeDTO.getSize() != null && !productSizeDTO.getSize().isEmpty()) {
            existingSize.setSize(productSizeDTO.getSize());
        }
        if (productSizeDTO.getPrice() != null) {
            existingSize.setPrice(productSizeDTO.getPrice());
        }
        if (productSizeDTO.getQuantity() != null) {
            existingSize.setQuantity(productSizeDTO.getQuantity());
        }

        // Cập nhật ProductVariant liên quan nếu thay đổi
        if (productSizeDTO.getProductVariantId() != null &&
            !existingSize.getProductVariant().getVariantId().equals(productSizeDTO.getProductVariantId())) {
            ProductVariant newVariant = productVariantRepository.findById(productSizeDTO.getProductVariantId())
                    .orElseThrow(() -> new EntityNotFoundException("New Product Variant not found with id: " + productSizeDTO.getProductVariantId()));

            // Xóa khỏi tập hợp kích thước của biến thể cũ (nếu ProductVariant có phương thức removeProductSize)
            // Điều này có thể yêu cầu xóa rõ ràng hơn nếu không được xử lý bởi orphanRemoval
            // existingSize.getProductVariant().removeProductSize(existingSize); // Giả sử phương thức này tồn tại

            // Đặt biến thể mới và thêm vào kích thước của biến thể mới
            existingSize.setProductVariant(newVariant);
            newVariant.addProductSize(existingSize);

            // Cập nhật stock/price cho cả sản phẩm cũ và mới
            updateProductStockAndPrice(originalProduct); // Cập nhật sản phẩm cũ
            updateProductStockAndPrice(newVariant.getProduct()); // Cập nhật sản phẩm mới
        }

        ProductSize updatedSize = productSizeRepository.save(existingSize);

        // Tính toán lại stock và khoảng giá của sản phẩm sau khi cập nhật
        updateProductStockAndPrice(updatedSize.getProductVariant().getProduct());

        return updatedSize;
    }

    @Override
    @Transactional
    public void deleteProductSize(Integer sizeId) {
        ProductSize sizeToDelete = productSizeRepository.findById(sizeId)
                .orElseThrow(() -> new EntityNotFoundException("Product Size not found with id: " + sizeId));

        Product product = sizeToDelete.getProductVariant().getProduct(); // Lấy sản phẩm trước khi xóa

        // Xóa khỏi tập hợp kích thước sản phẩm của biến thể để đảm bảo tính nhất quán hai chiều
        if (sizeToDelete.getProductVariant() != null) {
            sizeToDelete.getProductVariant().removeProductSize(sizeToDelete);
        }
        productSizeRepository.delete(sizeToDelete);

        // Cập nhật stock và khoảng giá của sản phẩm sau khi xóa
        updateProductStockAndPrice(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductSizeResponseDTO> getAllProductSizes(Pageable pageable) {
        Page<ProductSize> sizesPage = productSizeRepository.findAll(pageable);
        return sizesPage.map(ProductSizeResponseDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductSizeResponseDTO> getProductSizesByProductVariantId(Integer variantId) {
        if (!productVariantRepository.existsById(variantId)) {
            throw new EntityNotFoundException("Product Variant not found with id: " + variantId);
        }
        List<ProductSize> sizes = productSizeRepository.findByProductVariantVariantId(variantId);
        return sizes.stream()
                .map(ProductSizeResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Phương thức trợ giúp để cập nhật tổng stock và khoảng giá của sản phẩm
    @Transactional
    private void updateProductStockAndPrice(Product product) {
        List<ProductVariant> variantsForProduct = productVariantRepository.findByProductProductId(product.getProductId());

        int totalStock = 0;
        Integer minPrice = null;
        Integer maxPrice = null;

        for (ProductVariant variant : variantsForProduct) {
            if (variant.getProductSizes() != null) {
                for (ProductSize size : variant.getProductSizes()) {
                    totalStock += size.getQuantity();

                    // Chuyển đổi BigDecimal price sang Integer để so sánh và gán
                    Integer currentPrice = size.getPrice().intValue();

                    if (minPrice == null || currentPrice < minPrice) {
                        minPrice = currentPrice;
                    }
                    if (maxPrice == null || currentPrice > maxPrice) {
                        maxPrice = currentPrice;
                    }
                }
            }
        }

        // Giả sử setProductStock của Product entity mong đợi một Integer
        product.setProductStock(Integer.valueOf(totalStock));
        // Giả sử setProductMinPrice và setProductMaxPrice của Product entity mong đợi Integer
        product.setProductMinPrice(minPrice != null ? minPrice : 0);
        product.setProductMaxPrice(maxPrice != null ? maxPrice : 0);

        productRepository.save(product); // Lưu sản phẩm đã cập nhật
    }
}
