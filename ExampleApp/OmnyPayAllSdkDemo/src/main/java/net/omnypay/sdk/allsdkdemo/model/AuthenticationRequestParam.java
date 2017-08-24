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

/**
 * This class is the model representation of AuthenticationRequestParam
 */

import com.google.gson.annotations.SerializedName;

public class AuthenticationRequestParam {
    @SerializedName("merchant-id")
    private String merchantId = null;

    @SerializedName("username")
    private String username = null;

    @SerializedName("password")
    private String password = null;

    public AuthenticationRequestParam merchantId(String merchantId) {
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

    public AuthenticationRequestParam username(String username) {
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

    public AuthenticationRequestParam password(String password) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthenticationRequestParam that = (AuthenticationRequestParam) o;

        if (merchantId != null ? !merchantId.equals(that.merchantId) : that.merchantId != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null)
            return false;
        return password != null ? password.equals(that.password) : that.password == null;

    }

    @Override
    public int hashCode() {
        int result = merchantId != null ? merchantId.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AuthenticationRequestParam {\n");

        sb.append("    merchantId: ").append(toIndentedString(merchantId)).append("\n");
        sb.append("    username: ").append(toIndentedString(username)).append("\n");
        sb.append("    password: ").append(toIndentedString(password)).append("\n");
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
