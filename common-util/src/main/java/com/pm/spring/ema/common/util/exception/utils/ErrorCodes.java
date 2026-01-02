package com.pm.spring.ema.common.util.exception.utils;

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
    public static final String E1002 = "E1002-Field {0} should be 2 to 45 characters in length.";
    public static final String E1003 = "E1003-Field {0} is required.";
    public static final String E1004 = "E1004-Field {0} is required.";
    public static final String E1005 = "E1005-Field {0} is required.";
    public static final String E1006 = "E1006-Field {0} is required.";
    public static final String E1007 = "E1007-Field {0} should be 2 to 45 characters in length.";
    public static final String E1008 = "E1008-Field {0} is required.";
    public static final String E1009 = "E1009-Field {0} is required.";
    public static final String E1010 = "E1010-Field {0} is required.";
    public static final String E1011 = "E1011-Field {0} is required.";
    public static final String E1012 = "E1012-Field {0} should be 2 to 45 characters in length.";
    public static final String E1013 = "E1013-Field {0} is required.";
    public static final String E1014 = "E1014-Field {0} is required.";
    public static final String E1015 = "E1015-Field {0} is required.";
    public static final String E1016 = "E1016-Field {0} is required.";
    public static final String E1017 = "E1017-Field {0} is required.";
    public static final String E1018 = "E1018-Field {0} should be 2 to 45 characters in length.";
    public static final String E1019 = "E1019-Field {0} is required.";
    public static final String E1020 = "E1020-Field {0} is required.";
    public static final String E1021 = "E1020-Field {0} is required.";
    public static final String E1501 = "E1501-Category Name : {0} already exist";
    public static final String E1502 = "E1502-Could not find any Category with ID : {0}";
    public static final String E1503 = "E1503-Could not find any Category with ID : {0}";
    public static final String E1504 = "E1504-Could not find any Category with ID : {0}";
    public static final String E1505 = "E1505-Could not find any Category with ID : {0}";
    public static final String E1506 = "E1506-Could not find any Category with ID : {0}";
    public static final String E1507 = "E1507-Could not find any Category with ID : {0}";

    public static final String E1508 = "E1508-SubCategory Name : {0} already exist";
    public static final String E1509 = "E1509-Could not find any SubCategory with ID : {0}";
    public static final String E1510 = "E1510-Could not find any SubCategory with ID : {0}";
    public static final String E1511 = "E1511-Could not find any SubCategory with ID : {0}";
    public static final String E1512 = "E1512-Could not find any SubCategory with ID : {0}";
    public static final String E1513 = "E1513-Could not find any SubCategory with ID : {0}";
    public static final String E1514 = "E1514-Invalid Category with ID : {0}";

    public static final String E1550 = "E1550-Shopping Cart already exist with ID : {0}";
    public static final String E1551 = "E1551-Could not found shopping cart with ID : {0}";
    public static final String E1552 = "E1552-Could not found shopping cart with customerId : {0}";
    public static final String E1553 = "E1552-Could not found shopping cart with Id : {0}";

    //    Shopping-cart
    public static final String E1600 = "E1600-Field {0} is required.";
    public static final String E1601 = "E1601-Field {0} is required.";
    public static final String E1602 = "E1602-Field {0} is required.";
    public static final String E1603 = "E1603-Field {0} is required.";
    public static final String E1604 = "E1604-Field {0} is required.";
    public static final String E1605 = "E1605-Field {0} is required.";
    public static final String E1606 = "E1606-Could not find any CartItems with Shopping cart ID : {0}";
    public static final String E1607 = "E1607-Could not find any CartItems with ID : {0}";
    public static final String E1608 = "E1608-Could not find any CartItems with ID : {0}";
    public static final String E1609 = "E1609-Could not find any ShoppingCart with ID : {0}";




    // Product - 2001 to 3000
    public static final String E2001 = "E2001-Could not find any product with ID: ";
    public static final String E2002 = "E2002-Product already present with name: ";
    public static final String E2003 = "E2003-Product is not available at this moment: ";

    // Order  -  3001 to 4000
    public static final String E3001 = "E3001-Invalid Order Quantity for Product: ";
    public static final String E3002 = "E3002-Order not found with Id: ";
    public static final String E3003 = "E3003-Invalid order and Order Details";
    public static final String E3004 = "E3004-No Orders";

    //  user - 4001 to 5000 errors
    public static final String E4001 = "E4001-Field {0} is required.";
    public static final String E4002 = "E4002-Field {0} is required.";
    public static final String E4003 = "E4003-Field {0} is required.";
    public static final String E4004 = "E4004-Invalid {0}.";
    public static final String E4005 = "E4005-Field {0} is required.";
    public static final String E4006 = "E4006-Field {0} is required.";
    public static final String E4007 = "E4007-Field {0} is required.";
    public static final String E4008 = "E4008-Password not matched with confirm password.";
    public static final String E4009 = "E4009-Invalid value.";
    public static final String E4010 = "E4010-Field {0} is required.";
    public static final String E4011 = "E4011-Field {0} is required.";
    public static final String E4012 = "E4012-Username is already taken.";
    public static final String E4013 = "E4013-Email is already registered.";
    public static final String E4014 = "E4014-Username is between 5 to 15 characters in length.";
    public static final String E4015 = "E4015-User not authenticated.";
    public static final String E4016 = "E4016-User name not exist.";


    public static final String E4501 = "E0501-Could not find any user with ID : {0}";
    public static final String E4502 = "E0502-Could not find any user with ID : {0}";
    public static final String E4503 = "E0503-Could not find any user with ID : {0}";
    public static final String E4504 = "E0504-Could not find any user with ID : {0}";
    public static final String E4505 = "E0505-Could not find any user with ID : {0}";
    public static final String E4506 = "E0506-User name : {0} already exist";
    public static final String E4507 = "E4507-Could not find any user with ID : {0}";
    public static final String E4508 = "E0508-Could not find any user with ID : {0}";
    public static final String E4509 = "E4509-User email already registered with us";
    public static final String E4510 = "E4510-User email already registered with us";
    public static final String E4511 = "E4511-User name already registered with us";
    public static final String E4512 = "E4512-Could not find any user with ID : {0}";
    public static final String E4513 = "E4513-Could not find any user with ID : {0}";
    public static final String E4514 = "E4514-Could not find any user photo with ID : {0}";


    public static final String E0500 = "E0500-Internal Server Error - {0}";

    public static final String E5001 = "E5001-Field Country is required";
    public static final String E5002 = "E5002-Length should be 10 Characters";
    public static final String E5003 = "E5003-Length should be 6 Characters";
    public static final String E5004 = "E5004-Field Full Name is required";
    public static final String E5005 = "E5005-Field Mobile Number is required";
    public static final String E5006 = "E5006-Field Pincode is required";
    public static final String E5007 = "E5007-Field House Number is required";
    public static final String E5008 = "E5008-Field Area is required";
    public static final String E5009 = "E5009-Field Landmark is required";
    public static final String E5010 = "E5010-Field City is required";
    public static final String E5011 = "E5011-Field State is required";
    public static final String E5012 = "E5012-Maximum Address count reached";
    public static final String E5013 = "E5013-Couldn't found Address with id: ";
    public static final String E5014 = "E5014-Default Address not updated for addressId: ";
    public static final String E5015 = "E5015-Address not deleted for id: ";

    //     client

    public static final String E7001 = "E7001-Field Client name is required";
    public static final String E7002 = "E7002-Field Client Secret is required";
    public static final String E7003 = "E7003-Scope is required";
    public static final String E7004 = "E7004-Client Authentication method is required";
    public static final String E7005 = "E7005-Field authorizationGrantTypes should not be empty.";
    public static final String E7006 = "E7006-Field redirectUris should not be empty";
    public static final String E7007 = "E7007-Field postLogoutRedirectUris should not be empty";
    public static final String E7008 = "E7008-Client Secret expiry date is required";
    public static final String E7009 = "E7009-Field Client name is required";
    public static final String E7010 = "E7010-Field Client Secret is required";
    public static final String E7011 = "E7011-Client Authentication method is required";
    public static final String E7012 = "E7012-Field authorizationGrantTypes should not be empty.";
    public static final String E7013 = "E7013-Scope is required";
    public static final String E7014 = "E7014-Field redirectUris should not be empty";
    public static final String E7015 = "E7015-Field postLogoutRedirectUris should not be empty";
    public static final String E7016 = "E7016-Client Secret expiry date is required";

    public static final String E7501 = "E6001-Could not find any client with ID : {0}";
    public static final String E7502 = "E6002-Client Name: {0} already exist";
    public static final String E7503 = "E6003-Could not find any client with ID : {0}";
    public static final String E7504 = "E6004-Could not find any client with ID : {0}";


    public static final String E8001 = "E8001-Invalid OTP for id: {0}";

}