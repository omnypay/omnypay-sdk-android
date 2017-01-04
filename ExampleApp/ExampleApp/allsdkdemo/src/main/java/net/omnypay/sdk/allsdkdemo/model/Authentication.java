/**
 * Copyright 2016 OmnyPay Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.omnypay.sdk.allsdkdemo.model;

import com.google.gson.annotations.SerializedName;

/**
 * This class is the model representation of User Authentication
 */

public class Authentication {
    @SerializedName("merchant-shopper-id")
    private String merchantShopperId = null;
    @SerializedName("merchant-auth-token")
    private String merchantAuthToken = null;

    public Authentication() {
    }

    public Authentication merchantShopperId(String merchantShopperId) {
        this.merchantShopperId = merchantShopperId;
        return this;
    }

    public String getMerchantShopperId() {
        return this.merchantShopperId;
    }

    public void setMerchantShopperId(String merchantShopperId) {
        this.merchantShopperId = merchantShopperId;
    }

    public Authentication merchantAuthToken(String merchantAuthToken) {
        this.merchantAuthToken = merchantAuthToken;
        return this;
    }

    public String getMerchantAuthToken() {
        return this.merchantAuthToken;
    }

    public void setMerchantAuthToken(String merchantAuthToken) {
        this.merchantAuthToken = merchantAuthToken;
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            Authentication that = (Authentication)o;
            if(this.merchantShopperId != null) {
                if(this.merchantShopperId.equals(that.merchantShopperId)) {
                    return this.merchantAuthToken != null?this.merchantAuthToken.equals(that.merchantAuthToken):that.merchantAuthToken == null;
                }
            } else if(that.merchantShopperId == null) {
                return this.merchantAuthToken != null?this.merchantAuthToken.equals(that.merchantAuthToken):that.merchantAuthToken == null;
            }

            return false;
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.merchantShopperId != null?this.merchantShopperId.hashCode():0;
        result = 31 * result + (this.merchantAuthToken != null?this.merchantAuthToken.hashCode():0);
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Authentication {\n");
        sb.append("    merchantShopperId: ").append(this.toIndentedString(this.merchantShopperId)).append("\n");
        sb.append("    merchantAuthToken: ").append(this.toIndentedString(this.merchantAuthToken)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        return o == null?"null":o.toString().replace("\n", "\n    ");
    }
}
