//package com.steward.redis2;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;  
///**
// * 手动配置 redis 
// * @author KL-MZY
// *
// */
//@ConfigurationProperties(  prefix = "MyRedisProperties"  )  
//public class MyRedisProperties {  
//     private int database = 5;  
//     private String host = "192.168.0.71";  
//     private String password="klcgj";  
//     private int port = 6379;  
//     private MyRedisProperties.Pool pool;  
//     private MyRedisProperties.Sentinel sentinel;  
//      
//     public MyRedisProperties() {  
//     }  
//      
//     public String getHost() {  
//         return this.host;  
//     }  
//      
//     public void setHost(String host) {  
//         this.host = host;  
//     }  
//      
//     public int getPort() {  
//         return this.port;  
//     }  
//      
//     public void setPort(int port) {  
//         this.port = port;  
//     }  
//      
//     public String getPassword() {  
//         return this.password;  
//     }  
//      
//     public void setPassword(String password) {  
//         this.password = password;  
//     }  
//      
//     public MyRedisProperties.Pool getPool() {  
//         return this.pool;  
//     }  
//      
//     public void setPool(MyRedisProperties.Pool pool) {  
//         this.pool = pool;  
//     }  
//      
//     public int getDatabase() {  
//         return this.database;  
//     }  
//      
//     public void setDatabase(int database) {  
//         this.database = database;  
//     }  
//      
//     public MyRedisProperties.Sentinel getSentinel() {  
//         return this.sentinel;  
//     }  
//      
//     public void setSentinel(MyRedisProperties.Sentinel sentinel) {  
//         this.sentinel = sentinel;  
//     }  
//      
//     public static class Sentinel {  
//         private String master;  
//         private String nodes;  
//      
//         public Sentinel() {  
//         }  
//      
//         public String getMaster() {  
//             return this.master;  
//         }  
//      
//         public void setMaster(String master) {  
//             this.master = master;  
//         }  
//      
//         public String getNodes() {  
//             return this.nodes;  
//         }  
//      
//         public void setNodes(String nodes) {  
//             this.nodes = nodes;  
//         }  
//     }  
//      
//     public static class Pool {  
//         private int maxIdle = 8;  
//         private int minIdle = 0;  
//         private int maxActive = 8;  
//         private int maxWait = -1;  
//      
//         public Pool() {  
//         }  
//      
//         public int getMaxIdle() {  
//             return this.maxIdle;  
//         }  
//      
//         public void setMaxIdle(int maxIdle) {  
//             this.maxIdle = maxIdle;  
//         }  
//      
//         public int getMinIdle() {  
//             return this.minIdle;  
//         }  
//      
//         public void setMinIdle(int minIdle) {  
//             this.minIdle = minIdle;  
//         }  
//      
//         public int getMaxActive() {  
//             return this.maxActive;  
//         }  
//      
//         public void setMaxActive(int maxActive) {  
//             this.maxActive = maxActive;  
//         }  
//      
//         public int getMaxWait() {  
//             return this.maxWait;  
//         }  
//      
//         public void setMaxWait(int maxWait) {  
//             this.maxWait = maxWait;  
//         }  
//     }  
//}  
