package com.optimagrowth.licensingservice.controller;

import com.optimagrowth.licensingservice.model.License;
import com.optimagrowth.licensingservice.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {
    @Autowired
    LicenseService licenseService;

    @GetMapping(value = "/{licenseId}")
    public ResponseEntity<License> getLicense(@PathVariable(value = "organizationId") String organizationid,
                                              @PathVariable(value = "licenseId") String licenseId) {
        License license = licenseService.getLicense(licenseId, organizationid);
        license.add(linkTo(methodOn(LicenseController.class)
                        .getLicense(organizationid, license.getLicenseId()))
                        .withSelfRel(),
                linkTo(methodOn(LicenseController.class)
                        .createLicense(license, organizationid))
                        .withRel("createLicense"),
                linkTo(methodOn(LicenseController.class)
                        .updateLicense(license, organizationid))
                        .withRel("updateLicense"),
                linkTo(methodOn(LicenseController.class)
                        .deleteLicense(organizationid, license.getLicenseId()))
                        .withRel("deleteLicense"));

        return ResponseEntity.ok(license);
    }
    @PostMapping()
    public ResponseEntity<String> createLicense(@RequestBody License license, @PathVariable(value = "organizationId") String organizationId){
        String responseMessage = licenseService.createLicense(license, organizationId);
        return ResponseEntity.ok(responseMessage);
    }
    @PutMapping()
    public ResponseEntity<String> updateLicense(@RequestBody License license, @PathVariable(value = "organizationId") String organizationId) {
        return ResponseEntity.ok(licenseService.updateLicense(organizationId, license));
    }
    @DeleteMapping(value = "/{licenseId}")
    public ResponseEntity<String> deleteLicense(@PathVariable(value = "licenseId") String licenseId,
                                                @PathVariable(value = "organizationId") String organizationId) {
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId, organizationId));
    }
}
