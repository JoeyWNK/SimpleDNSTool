package server;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class Requester {
    /**
    * 获取DNS服务器信息
    *
    * @param domain  要获取DNS信息的域名
    * @param provider      DNS服务器
    * @param types   信息类型 "A"(IP信息)，"MX"
    * @param timeout 请求超时
    * @param retryCount    重试次数
    *
    * @return 所有信息组成的数组
    *
    * @throws NamingException
    *        
    */

    @SuppressWarnings("rawtypes" )
    private static ArrayList<String> getDNSRecs(String domain, String provider,
               String [] types, int timeout, int retryCount) throws NamingException {
         
         ArrayList<String> results = new ArrayList<String>(15);
         
         Hashtable<String, String> env = new Hashtable<String, String>();
         
         env.put( "java.naming.factory.initial" ,
                      "com.sun.jndi.dns.DnsContextFactory" );
         
         env.put(Context.AUTHORITATIVE, "true");
         
          //设置域名服务器
         env.put(Context.PROVIDER_URL, "dns://" + provider);
         
          // 连接时间
         env.put( "com.sun.jndi.dns.timeout.initial" , String.valueOf(timeout));
         
          // 连接次数
         env.put( "com.sun.jndi.dns.timeout.retries" , String.valueOf(retryCount));
         
         DirContext ictx = new InitialDirContext(env);
         Attributes attrs = ictx.getAttributes(domain, types);
         
          for (Enumeration e = attrs.getAll(); e.hasMoreElements();) {
               Attribute a = (Attribute) e.nextElement();
                int size = a.size();
                for (int i = 0; i < size; i++) {
                     results.add((String) a.get(i));
               }
         }
          return results;
   }

    /**
    * 获取域名所有IP
    * @param domain  域名
    * @param dnsServers    DNS服务器列表
    * @param timeout 请求超时
    * @param retryCount    重试次数
    * @return
    */
    public static Set<String> getAllIP(String domain, String[] dnsServers,
                int timeout, int retryCount) {
         Set<String> ips = new HashSet<String>();
         
          for(String dnsServer: dnsServers) {
               List<String> ipList;
                try {
                     ipList = getDNSRecs(domain, dnsServer, new String[]{"A"},
                                 timeout, retryCount);
               } catch (NamingException e) {
                      continue;
               }
               ips.addAll(ipList);
         }       
          return ips;
   }
}
