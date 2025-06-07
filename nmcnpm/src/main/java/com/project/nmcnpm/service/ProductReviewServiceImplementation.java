package com.project.nmcnpm.service;

import com.project.nmcnpm.dao.ProductReviewRepository;
import com.project.nmcnpm.dao.ReviewImageRepository; 
import com.project.nmcnpm.dao.ProductRepository; 
import com.project.nmcnpm.dao.UserRepository;     
import com.project.nmcnpm.dto.ProductReviewDTO;
import com.project.nmcnpm.dto.ProductReviewResponseDTO;
import com.project.nmcnpm.dto.ReviewImageDTO; 
import com.project.nmcnpm.entity.Product;
import com.project.nmcnpm.entity.ProductReview;
import com.project.nmcnpm.entity.ReviewImage;
import com.project.nmcnpm.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductReviewServiceImplementation implements ProductReviewService {
    private final ProductReviewRepository productReviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    public ProductReviewServiceImplementation(ProductReviewRepository productReviewRepository,
                                              ReviewImageRepository reviewImageRepository,
                                              ProductRepository productRepository,
                                              UserRepository userRepository) {
        this.productReviewRepository = productReviewRepository;
        this.reviewImageRepository = reviewImageRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }
    @Override
    @Transactional
    public ProductReview createProductReview(ProductReviewDTO productReviewDTO) {
        Product product = productRepository.findById(productReviewDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productReviewDTO.getProductId()));
        User user = userRepository.findById(productReviewDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + productReviewDTO.getUserId()));
        ProductReview productReview = new ProductReview();
        productReview.setProduct(product);
        productReview.setUser(user);
        productReview.setRating(productReviewDTO.getRating());
        productReview.setReviewText(productReviewDTO.getReviewText());
        if (productReviewDTO.getReviewImages() != null && !productReviewDTO.getReviewImages().isEmpty()) {
            for (ReviewImageDTO imageDTO : productReviewDTO.getReviewImages()) {
                ReviewImage reviewImage = new ReviewImage();
                reviewImage.setImageUrl(imageDTO.getImageUrl());
                productReview.addReviewImage(reviewImage); 
            }
        }
        ProductReview savedReview = productReviewRepository.save(productReview);
        updateProductRatingAndReviewCount(product);
        return savedReview;
    }
    @Override
    @Transactional(readOnly = true)
    public ProductReviewResponseDTO getProductReviewById(Integer reviewId) {
        ProductReview productReview = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Product Review not found with id: " + reviewId));
        return new ProductReviewResponseDTO(productReview);
    }
    @Override
    @Transactional
    public ProductReview updateProductReview(Integer reviewId, ProductReviewDTO productReviewDTO) {
        ProductReview existingReview = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Product Review not found with id: " + reviewId));
        Product originalProduct = existingReview.getProduct(); 
        if (productReviewDTO.getRating() != null) {
            existingReview.setRating(productReviewDTO.getRating());
        }
        if (productReviewDTO.getReviewText() != null) {
            existingReview.setReviewText(productReviewDTO.getReviewText());
        }
        if (productReviewDTO.getProductId() != null && !existingReview.getProduct().getProductId().equals(productReviewDTO.getProductId())) {
            Product newProduct = productRepository.findById(productReviewDTO.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("New Product not found with id: " + productReviewDTO.getProductId()));
            existingReview.setProduct(newProduct);
            updateProductRatingAndReviewCount(originalProduct); 
            updateProductRatingAndReviewCount(newProduct); 
        }
        if (productReviewDTO.getUserId() != null && !existingReview.getUser().getUserId().equals(productReviewDTO.getUserId())) {
            User newUser = userRepository.findById(productReviewDTO.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("New User not found with id: " + productReviewDTO.getUserId()));
            existingReview.setUser(newUser);
        }
        if (productReviewDTO.getReviewImages() != null) {
            if (existingReview.getReviewImages() != null) {
                reviewImageRepository.deleteAll(existingReview.getReviewImages());
                existingReview.getReviewImages().clear();
            }
            for (ReviewImageDTO imageDTO : productReviewDTO.getReviewImages()) {
                ReviewImage newImage = new ReviewImage();
                newImage.setImageUrl(imageDTO.getImageUrl());
                existingReview.addReviewImage(newImage);
            }
        }
        ProductReview updatedReview = productReviewRepository.save(existingReview);
        updateProductRatingAndReviewCount(updatedReview.getProduct());
        return updatedReview;
    }
    @Override
    @Transactional
    public void deleteProductReview(Integer reviewId) {
        ProductReview reviewToDelete = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Product Review not found with id: " + reviewId));
        Product product = reviewToDelete.getProduct(); 
        productReviewRepository.delete(reviewToDelete);
        updateProductRatingAndReviewCount(product);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<ProductReviewResponseDTO> getAllProductReviews(Pageable pageable) {
        Page<ProductReview> reviewsPage = productReviewRepository.findAll(pageable);
        return reviewsPage.map(ProductReviewResponseDTO::new);
    }
    @Override
    @Transactional(readOnly = true)
    public List<ProductReviewResponseDTO> getProductReviewsByProductId(Integer productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product not found with id: " + productId);
        }
        List<ProductReview> reviews = productReviewRepository.findByProductProductId(productId);
        return reviews.stream()
                .map(ProductReviewResponseDTO::new)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    public List<ProductReviewResponseDTO> getProductReviewsByUserId(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        List<ProductReview> reviews = productReviewRepository.findByUserUserId(userId);
        return reviews.stream()
                .map(ProductReviewResponseDTO::new)
                .collect(Collectors.toList());
    }
    @Transactional 
    private void updateProductRatingAndReviewCount(Product product) {
        List<ProductReview> reviewsForProduct = productReviewRepository.findByProductProductId(product.getProductId());
        int totalRating = 0;
        int reviewCount = reviewsForProduct.size();
        if (reviewCount > 0) {
            for (ProductReview review : reviewsForProduct) {
                totalRating += review.getRating();
            }
            product.setProductStar(totalRating / reviewCount);
        } else {
            product.setProductStar(0); 
        }
        product.setProductReview(reviewCount); 
        product.setNumOfProductStar(totalRating); 
        productRepository.save(product); 
    }
}
