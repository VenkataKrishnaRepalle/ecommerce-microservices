package com.pm.spring.ema.common.util.exception.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class ErrorCodes {
    //  brand - 1 to 1000 errors
    public static final String E0001 = "Field  is required.";
    public static final String E0002 = "Field  should be 2 to 45 characters in length";
    public static final String E0003 = "Field  is required.";
    public static final String E0004 = "Field  is required.";
    public static final String E0005 = "Field  is required.";
    public static final String E0006 = "Field  should be 2 to 45 characters in length";
    public static final String E0007 = "Field  is required.";
    public static final String E0008 = "Field  is required.";
    public static final String E0009 = "Field  is required.";

    public static final String E0501 = "Could not find any brand with ID :";
    public static final String E0502 = "Brand name already exist with id :";


    public static final String E1001 = "Field  is required.";
    public static final String E1002 = "Field  should be 2 to 45 characters in length.";
    public static final String E1003 = "Field  is required.";
    public static final String E1004 = "Field  is required.";
    public static final String E1005 = "Field  is required.";
    public static final String E1006 = "Field  is required.";
    public static final String E1007 = "Field  should be 2 to 45 characters in length.";
    public static final String E1008 = "Field  is required.";
    public static final String E1009 = "Field  is required.";
    public static final String E1010 = "Field  is required.";
    public static final String E1011 = "Field  is required.";
    public static final String E1012 = "Field  should be 2 to 45 characters in length.";
    public static final String E1013 = "Field  is required.";
    public static final String E1014 = "Field  is required.";
    public static final String E1015 = "Field  is required.";
    public static final String E1016 = "Field  is required.";
    public static final String E1017 = "Field  is required.";
    public static final String E1018 = "Field  should be 2 to 45 characters in length.";
    public static final String E1019 = "Field  is required.";
    public static final String E1020 = "Field  is required.";
    public static final String E1021 = "Field  is required.";
    public static final String E1501 = "Category Name :  already exist";
    public static final String E1502 = "Could not find any Category with ID : ";
    public static final String E1503 = "Could not find any Category with ID : ";
    public static final String E1504 = "Could not find any Category with ID : ";
    public static final String E1505 = "Could not find any Category with ID : ";
    public static final String E1506 = "Could not find any Category with ID : ";
    public static final String E1507 = "Could not find any Category with ID : ";

    public static final String E1508 = "SubCategory Name :  already exist";
    public static final String E1509 = "Could not find any SubCategory with ID : ";
    public static final String E1510 = "Could not find any SubCategory with ID : ";
    public static final String E1511 = "Could not find any SubCategory with ID : ";
    public static final String E1512 = "Could not find any SubCategory with ID : ";
    public static final String E1513 = "Could not find any SubCategory with ID : ";
    public static final String E1514 = "Invalid Category with ID : ";

    public static final String E1550 = "Shopping Cart already exist with ID : ";
    public static final String E1551 = "Could not found shopping cart with ID : ";
    public static final String E1552 = "Could not found shopping cart with customerId : ";
    public static final String E1553 = "Could not found shopping cart with Id : ";

    //    Shopping-cart
    public static final String E1600 = "Field  is required.";
    public static final String E1601 = "Field  is required.";
    public static final String E1602 = "Field  is required.";
    public static final String E1603 = "Field  is required.";
    public static final String E1604 = "Field  is required.";
    public static final String E1605 = "Field  is required.";
    public static final String E1606 = "Could not find any CartItems with Shopping cart ID : ";
    public static final String E1607 = "Could not find any CartItems with ID : ";
    public static final String E1608 = "Could not find any CartItems with ID : ";
    public static final String E1609 = "Could not find any ShoppingCart with ID : ";




    // Product - 2001 to 3000
    public static final String E2001 = "Could not find any product with ID: ";
    public static final String E2002 = "Product already present with name: ";
    public static final String E2003 = "Product is not available at this moment: ";

    // Order  -  3001 to 4000
    public static final String E3001 = "Invalid Order Quantity for Product: ";
    public static final String E3002 = "Order not found with Id: ";
    public static final String E3003 = "Invalid order and Order Details";
    public static final String E3004 = "No Orders";

    //  user - 4001 to 5000 errors
    public static final String E4001 = "Field  is required.";
    public static final String E4002 = "Field  is required.";
    public static final String E4003 = "Field  is required.";
    public static final String E4004 = "Invalid .";
    public static final String E4005 = "Field  is required.";
    public static final String E4006 = "Field  is required.";
    public static final String E4007 = "Field  is required.";
    public static final String E4008 = "Password not matched with confirm password.";
    public static final String E4009 = "Invalid value.";
    public static final String E4010 = "Field  is required.";
    public static final String E4011 = "Field  is required.";
    public static final String E4012 = "Username is already taken.";
    public static final String E4013 = "Email is already registered.";
    public static final String E4014 = "Username is between 5 to 15 characters in length.";
    public static final String E4015 = "User not authenticated.";
    public static final String E4016 = "User name not exist.";


    public static final String E4501 = "Could not find any user with ID :";
    public static final String E4502 = "Could not find any user with ID :";
    public static final String E4503 = "User name already exist: ";
    public static final String E4504 = "Could not find any user with ID :";
    public static final String E4515 = "User email already registered with us";
    public static final String E4506 = "Could not find any user photo with ID :";


    public static final String E0500 = "Internal Server Error - ";

    public static final String E5001 = "Field Country is required";
    public static final String E5002 = "Length should be 10 Characters";
    public static final String E5003 = "Length should be 6 Characters";
    public static final String E5004 = "Field Full Name is required";
    public static final String E5005 = "Field Mobile Number is required";
    public static final String E5006 = "Field Pincode is required";
    public static final String E5007 = "Field House Number is required";
    public static final String E5008 = "Field Area is required";
    public static final String E5009 = "Field Landmark is required";
    public static final String E5010 = "Field City is required";
    public static final String E5011 = "Field State is required";
    public static final String E5012 = "Maximum Address count reached";
    public static final String E5013 = "Couldn't found Address with id: ";
    public static final String E5014 = "Default Address not updated for addressId: ";
    public static final String E5015 = "Address not deleted for id: ";

    //     client

    public static final String E7001 = "Field Client name is required";
    public static final String E7002 = "Field Client Secret is required";
    public static final String E7003 = "Scope is required";
    public static final String E7004 = "Client Authentication method is required";
    public static final String E7005 = "Field authorizationGrantTypes should not be empty.";
    public static final String E7006 = "Field redirectUris should not be empty";
    public static final String E7007 = "Field postLogoutRedirectUris should not be empty";
    public static final String E7008 = "Client Secret expiry date is required";
    public static final String E7009 = "Field Client name is required";
    public static final String E7010 = "Field Client Secret is required";
    public static final String E7011 = "Client Authentication method is required";
    public static final String E7012 = "Field authorizationGrantTypes should not be empty.";
    public static final String E7013 = "Scope is required";
    public static final String E7014 = "Field redirectUris should not be empty";
    public static final String E7015 = "Field postLogoutRedirectUris should not be empty";
    public static final String E7016 = "Client Secret expiry date is required";

    public static final String E7501 = "Could not find any client with ID :";
    public static final String E7502 = "Client Name:  already exist";
    public static final String E7503 = "Could not find any client with ID :";
    public static final String E7504 = "Could not find any client with ID :";


    public static final String E8001 = "Invalid OTP for id: ";

}