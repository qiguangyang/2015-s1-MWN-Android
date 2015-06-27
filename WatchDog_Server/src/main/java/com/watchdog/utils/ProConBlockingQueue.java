package com.watchdog.utils;

import java.util.concurrent.ArrayBlockingQueue;

public class ProConBlockingQueue {  
	  
	  //定义阻塞FIFO队列  
	  private  ArrayBlockingQueue<Integer> buffer = new ArrayBlockingQueue<Integer>(Integer.MAX_VALUE);  
	  
	  public class ProducerTask implements Runnable {  
	    public void run() {  
	      try {  
	        int i = 1;  
	        while (true) {  
	          System.out.println("produce " + i);  
	          buffer.put(i++);  
	          Thread.sleep((int)(Math.random() * 1000));  
	        }  
	      } catch (InterruptedException ex) {  
	        ex.printStackTrace();  
	      }  
	    }  
	  }  
	  
	  public class ConsumerTask implements Runnable {  
	    public void run() {  
	      try {  
	        while (true) {  
	          System.out.println("consume " + buffer.take());  
	          Thread.sleep((int)(Math.random() * 1000));  
	        }  
	      } catch (InterruptedException ex) {  
	        ex.printStackTrace();  
	      }  
	    }  
	  }  
	}  
