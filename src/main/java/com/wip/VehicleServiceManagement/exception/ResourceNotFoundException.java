package com.wip.VehicleServiceManagement.exception;
/**
 * ResourceNotFoundException.
 *
 * @author Sridevi Srikumar
 */

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}