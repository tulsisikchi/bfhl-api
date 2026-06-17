package com.dypatil.bfhl.service;

import com.dypatil.bfhl.dto.BfhlRequest;
import com.dypatil.bfhl.dto.BfhlResponse;

public interface BfhlService {
    BfhlResponse process(BfhlRequest request, String requestId);
}