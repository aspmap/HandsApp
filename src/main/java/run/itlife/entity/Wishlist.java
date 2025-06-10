package run.itlife.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@Entity
public class Wishlist {
    @Id
    @Column(name="wishlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;
    @Column(name = "photo")
    private String photo;
    @Column(name = "link")
    private String link;
    @Column(name = "name_wish")
    private String nameWish;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private String price;
    @Column(name = "is_secret")
    private boolean isSecret;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public String getNameWish() {
        return nameWish;
    }

    public void setNameWish(String nameWish) {
        this.nameWish = nameWish;
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getSecret() {
        return isSecret;
    }

    public void setSecret(Boolean secret) {
        isSecret = secret;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}