package com.spring6.common.exeption;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class ErrorCodes {
    //  brand - 1 to 1000 errors
    public static final String E0001 = "E0001-Field {0} is required.";
    public static final String E0002 = "E0002-Field {0} should be 2 to 45 characters in length";
    public static final String E0003 = "E0003-Field {0} is required.";
    public static final String E0004 = "E0004-Field {0} is required.";
    public static final String E0005 = "E0005-Field {0} is required.";
    public static final String E0006 = "E0006-Field {0} should be 2 to 45 characters in length";
    public static final String E0007 = "E0007-Field {0} is required.";
    public static final String E0008 = "E0007-Field {0} is required.";
    public static final String E0009 = "E0008-Field {0} is required.";

    public static final String E0501 = "E0501-Could not find any brand with ID : {0}";
    public static final String E0502 = "E0502-Could not find any brand with ID : {0}";
    public static final String E0503 = "E0503-Could not find any brand with ID : {0}";
    public static final String E0504 = "E0504-Could not find any brand with ID : {0}";
    public static final String E0505 = "E0505-Could not find any brand with ID : {0}";
    public static final String E0506 = "E0506-Brand name : {0} already exist";
    public static final String E0507 = "E0507-Brand name already exist with id : {0}";
    public static final String E0508 = "E0508-Could not find any brand with ID : {0}";



    public static final String E1001 = "E1001-Field {0} is required.";
    //  user - 4001 to 5000 errors
    public static final String E4001 =  "E4001-Field {0} is required.";
    public static final String E4002 =  "E4002-Field {0} is required.";
    public static final String E4003 =  "E4003-Field {0} is required.";
    public static final String E4004 =  "E4004-Invalid {0}.";
    public static final String E4005 =  "E4005-Field {0} is required.";
    public static final String E4006 =  "E4006-Field {0} is required.";
    public static final String E4007 =  "E4007-Field {0} is required.";
    public static final String E4008 =  "E4008-Password not matched with confirm password.";
    public static final String E4009 =  "E4009-Field {0} is required.";
    public static final String E4010 =  "E4010-Field {0} is required.";
    public static final String E0500 =  "E0500-Internal Server Error - {0}";
}