package net.omnypay.sdk.exampleapp.model;

/**
 * Created by MikiP on 29-06-2017.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class PoSTerminal implements Serializable{
    @SerializedName("id")
    private String id = null;

    @SerializedName("is-active")
    private Boolean isActive = null;

    @SerializedName("created")
    private String created = null;

    @SerializedName("modified")
    private String modified = null;

    @SerializedName("label")
    private String label = null;

    @SerializedName("merchant-id")
    private String merchantId = null;

    @SerializedName("merchant-pos-id")
    private String merchantPosId = null;

    public PoSTerminal id(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantPosId() {
        return merchantPosId;
    }

    public void setMerchantPosId(String merchantPosId) {
        this.merchantPosId = merchantPosId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoSTerminal that = (PoSTerminal) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (isActive != null ? !isActive.equals(that.isActive) : that.isActive != null)
            return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (modified != null ? !modified.equals(that.modified) : that.modified != null)
            return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;
        if (merchantId != null ? !merchantId.equals(that.merchantId) : that.merchantId != null)
            return false;
        return merchantPosId != null ? merchantPosId.equals(that.merchantPosId) : that.merchantPosId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        result = 31 * result + (merchantId != null ? merchantId.hashCode() : 0);
        result = 31 * result + (merchantPosId != null ? merchantPosId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PoSTerminal {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    isActive: ").append(toIndentedString(isActive)).append("\n");
        sb.append("    created: ").append(toIndentedString(created)).append("\n");
        sb.append("    modified: ").append(toIndentedString(modified)).append("\n");
        sb.append("    label: ").append(toIndentedString(label)).append("\n");
        sb.append("    merchantId: ").append(toIndentedString(merchantId)).append("\n");
        sb.append("    merchantPosId: ").append(toIndentedString(merchantPosId)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}

