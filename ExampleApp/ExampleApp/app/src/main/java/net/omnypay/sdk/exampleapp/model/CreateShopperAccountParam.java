package net.omnypay.sdk.exampleapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * This class is the model representation of CreateShopperAccountParam
 */

public class CreateShopperAccountParam {
    @SerializedName("merchant-id")
    private String merchantId = null;

    @SerializedName("email")
    private String email = null;

    @SerializedName("username")
    private String username = null;

    @SerializedName("password")
    private String password = null;

    @SerializedName("loyalty-points")
    private Long loyaltyPoints = null;

    public CreateShopperAccountParam merchantId(String merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    /**
     * Get merchantId
     * @return merchantId
     **/
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public CreateShopperAccountParam email(String email) {
        this.email = email;
        return this;
    }

    /**
     * Get email
     * @return email
     **/
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CreateShopperAccountParam username(String username) {
        this.username = username;
        return this;
    }

    /**
     * Get username
     * @return username
     **/
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CreateShopperAccountParam password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Get password
     * @return password
     **/
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CreateShopperAccountParam loyaltyPoints(Long loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
        return this;
    }

    /**
     * Get loyaltyPoints
     * @return loyaltyPoints
     **/
    public Long getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Long loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateShopperAccountParam that = (CreateShopperAccountParam) o;

        if (merchantId != null ? !merchantId.equals(that.merchantId) : that.merchantId != null)
            return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null)
            return false;
        return loyaltyPoints != null ? loyaltyPoints.equals(that.loyaltyPoints) : that.loyaltyPoints == null;

    }

    @Override
    public int hashCode() {
        int result = merchantId != null ? merchantId.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (loyaltyPoints != null ? loyaltyPoints.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CreateShopperAccountParam {\n");

        sb.append("    merchantId: ").append(toIndentedString(merchantId)).append("\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    username: ").append(toIndentedString(username)).append("\n");
        sb.append("    password: ").append(toIndentedString(password)).append("\n");
        sb.append("    loyaltyPoints: ").append(toIndentedString(loyaltyPoints)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}