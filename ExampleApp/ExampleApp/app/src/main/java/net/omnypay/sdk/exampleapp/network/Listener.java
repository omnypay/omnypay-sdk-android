package net.omnypay.sdk.exampleapp.network;

import net.omnypay.sdk.wrapper.OmnyPayError;

/**
 * Created by MikiP on 09-12-2016.
 */

public interface Listener {

    void onResult(String response);

    void onError(OmnyPayError omnyPayError);

}
