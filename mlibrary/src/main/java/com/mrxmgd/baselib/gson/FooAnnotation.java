/*
 *
 *
 *    Copyright (c) 2018. mrxmgd.com
 *
 *     @author MRXMGD <mrxmgd@gmail.com>
 *
 *
 */

package com.mrxmgd.baselib.gson;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FooAnnotation {
   // some implementation here
}

//    Gson gson = new GsonBuilder()
//            .setExclusionStrategies( new FooAnnotationExclusionStrategy())
//            .create();