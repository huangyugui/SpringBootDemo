package com.example.concurrent.queue;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueTest {
    
    public static void test1(){
        BlockingQueue<String> bq = new ArrayBlockingQueue<String>(5);
        bq.offer("1");
        bq.offer("2");
        bq.offer("3");
        bq.offer("4");
        bq.offer("5");
        try {
            System.out.println(bq.offer("2", 2, TimeUnit.SECONDS));
            bq.poll(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            bq.put("1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            bq.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(bq.offer("1"));
        bq.stream().forEach(System.out::println);
        for (String string : bq) {
            System.out.println(bq.remove());
        }
//        bq.remove();
//        System.out.println(bq.poll());
//        System.out.println(bq.peek());
       
//        System.out.println(bq.remainingCapacity());
        
    }
    
    public static void test2(){
        BlockingQueue<String> bq = new LinkedBlockingDeque<String>();
        try {
            bq.put("a");
            bq.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            bq.offer("b", 2, TimeUnit.SECONDS);
            bq.poll(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bq.offer("c");
        bq.poll();
        bq.add("d");
        bq.peek();
        bq.remove();
    }
    
    public static void test3(){
        BlockingQueue<String> bq = new SynchronousQueue<String>();
        Thread t = new Thread(() -> {
            while(true){
                try {
                    System.out.println(bq.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        for(int i = 0; i < 10; i++){
//            bq.add("aaaa" + i);
//            bq.offer("aa" + i);
            try {
                bq.put("a" + i);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public static void test4(){
        BlockingQueue<String> bq = new PriorityBlockingQueue<String>();
        try {
            bq.put("aaa");
            bq.offer("sss");
            bq.add("ccc");
            bq.addAll(Arrays.asList("bbb", "ttt"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bq.stream().forEach(System.out::println);
    }
    
    public static void test5(){
        BlockingQueue<Person> bq = new DelayQueue<Person>();
        bq.offer(new Person("aaa", 5));
        bq.offer(new Person("bbb", 3));
        bq.offer(new Person("ccc", 4));
        bq.offer(new Person("ddd", 1));
        Thread t = new Thread(() -> {
           while(true){
               try {
                System.out.println(bq.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           }
        });
        t.start();
    }
    
    

    public static void main(String[] args) {
        test5();
    }
    
}

class Person implements Delayed{
    
    private String name;
    
    private Integer age;
    
    private Long trigger = 0l;

    public Person() {
        super();
    }

    public Person(String name, Integer age) {
        super();
        this.name = name;
        this.age = age;
        this.trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(this.getAge()*1000, TimeUnit.MILLISECONDS);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + "]";
    }
    
    @Override
    public int compareTo(Delayed o) {
        if(o == null){
            return 1;
        }
        Person other = (Person)o;
        return this.age - other.getAge();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
    }
    
}
