package com.atman.server.VendorModule.Service;

import com.atman.server.VendorModule.Service.Impl.VendorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorServiceImpl implements VendorService {}
