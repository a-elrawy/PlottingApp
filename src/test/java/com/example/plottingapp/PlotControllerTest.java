package com.example.plottingapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PlotControllerTest {

    @Test
    void preprocess() {
        PlotController plotController =new PlotController();
        Random random = new Random();
        int[] arr = random.ints(20002,0,5000).toArray();
        for (int i = 0; i < 20000; i++) {
            Assertions.assertEquals(arr[i]*arr[i+1]-arr[i+2],plotController.preprocess(arr[i] +"*"+arr[i+1]+"-"+arr[i+2]));
            Assertions.assertEquals(arr[i]*arr[i+1]+arr[i+2],plotController.preprocess(arr[i] +"*"+arr[i+1]+"+"+arr[i+2]));
            Assertions.assertEquals(arr[i]*arr[i+1]*arr[i+2],plotController.preprocess(arr[i] +"*"+arr[i+1]+"*"+arr[i+2]));
            Assertions.assertEquals(arr[i]*(arr[i+1]+arr[i+2]),plotController.preprocess(arr[i] +"*("+arr[i+1]+"+"+arr[i+2]+")"));
            Assertions.assertEquals(arr[i]+(int) Math.pow(arr[i+1],arr[i+2]),plotController.preprocess(arr[i] +"+"+arr[i+1]+"^"+arr[i+2]));
        }
    }
}