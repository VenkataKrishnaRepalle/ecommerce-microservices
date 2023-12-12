package com.pm.spring.ema.common.util.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class ErrorCodes {
    //  message-service - 1 to 1000 errors
    public static final String E0001 = "E0001-Field {0} is required.";
    public static final String E0002 = "E0002-Field {0} should be 2 to 45 characters in length";
    public static final String E0003 = "E0003-Field {0} is required.";
    public static final String E0004 = "E0004-Field {0} is required.";
    public static final String E0005 = "E0005-Field {0} is required.";
    public static final String E0006 = "E0006-Field {0} should be 2 to 45 characters in length";
    public static final String E0007 = "E0007-Field {0} is required.";
    public static final String E0008 = "E0008-Field {0} is required.";
    public static final String E0009 = "E0009-Field {0} is required.";

    public static final String E0501 = "E0501-Could not find any brand with ID : {0}";
    public static final String E0502 = "E0502-Could not find any brand with ID : {0}";
    public static final String E0503 = "E0503-Could not find any brand with ID : {0}";
    public static final String E0504 = "E0504-Could not find any brand with ID : {0}";
    public static final String E0505 = "E0505-Could not find any brand with ID : {0}";
    public static final String E0506 = "E0506-Brand name : {0} already exist";
    public static final String E0507 = "E0507-Brand name already exist with id : {0}";
    public static final String E0508 = "E0508-Could not find any brand with ID : {0}";


    public static final String E0500 = "E0500-Internal Server Error - {0}";

    public static final String E1001 = "E1001-Field {0} is required.";
    public static final String E1002 = "E1002-Field {0} is required.";
    public static final String E1003 = "E1003-Field {0} is required.";
    public static final String E1004 = "E1004-Field {0} should be 2 to 25 characters in length.";
    public static final String E1005 = "E1005-Field {0} is not Invalid UUID";
    public static final String E1006 = "E1006-Field {0} is required.";
    public static final String E1007 = "E1007-Field {0} should be 2 to 25 characters in length.";
    public static final String E1008 = "E1006-Field {0} is required.";
    public static final String E1009 = "E1006-Field {0} is required.";


    public static final String E1501 = "E1501- Contact UserId : {0} already exist";
    public static final String E1502 = "E1502- Could not find any Contact with Name : {0}";
    public static final String E1503 = "E1503- Could not find any Contact with Id : {0}";
    public static final String E1504 = "E1504- Could not find any Contact with userId : {0}";
    public static final String E1505 = "E1505- Could not find any Contact with contactUserId : {0}";
    public static final String E1506 = "E1506- Could not find any Contact with contactUserId : {0}";

    //Authentication-service 2001-3000
    public static final String E2001 = "E2001-Auth Header is required.";
    public static final String E2002 = "E2002-Account already exists with Username : {0}";
    public static final String E2003 = "E2003-Account already exists with Phone Number : {0}";
    public static final String E2004 = "E2004-Account already exists with Email : {0}";
    public static final String E2005 = "E2005-Account already exists with Email : {0} and Phone Number : {1} and Username : {2}";
    public static final String E2006 = "E2006-Password and Confirm Password not matched";
    public static final String E2007 = "E2007-Password must be at least 8 characters long";
    public static final String E2008 = "E2008-Account not exists with id : {0}";
    public static final String E2009 = "E2009-Only images (JPEG, PNG, GIF) are allowed";
    public static final String E2010 = "E2010-Entered Password is Incorrect, Please Enter Correct Password";
    public static final String E2011 = "E2011-Entered user identifier not exists";
    public static final String E2012 = "E2012-Entered old Password is incorrect";
    public static final String E2013 = "E2013-Entered New Password is same as Old Password, Please use different Password";
    public static final String E2014 = "E2014-Entered Password and Confirm Password not matched, Please Correct it";
    public static final String E2015 = " length Should be equal or more than 8 characters";
    public static final String E2016 = "Invalid File";
    public static final String E2017 = "File not saved";

    //mail-service 3001-4000
    public static final String E3001 = "E3001-Please request new OTP";
    public static final String E3002 = "E3002-Here is your One Time Password for Forgot Password - Expire in 2 minutes";
    public static final String E3003 = "E3003-Please Re-enter OTP or request new OTP";
    public static final String E3004 = "E3004-Entered OTP is incorrect, Please Re-Enter";
    public static final String E3005 = "E3005-Entered OTP is incorrect, Please generate new OTP using Resend otp-code";
}