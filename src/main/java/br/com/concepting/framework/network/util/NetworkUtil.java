package br.com.concepting.framework.network.util;

import br.com.concepting.framework.util.NumberUtil;
import br.com.concepting.framework.util.StringUtil;
import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.net.util.SubnetUtils.SubnetInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsible to manipulate network services.
 *
 * @author fvilarinho
 * @since 3.0.0
 *
 * <pre>Copyright (C) 2007 Innovative Thinking.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <a href="https://www.gnu.org/licenses"></a>.</pre>
 */
public class NetworkUtil{
    public static boolean isIp(String ip) {
        String[] parts = ip.split("\\.", -1);

        if (parts.length != 4)
            return false;

        for (String part : parts) {
            if (part.isEmpty() || part.length() > 3)
                return false;

            try {
                int value = Integer.parseInt(part);

                if (value < 0 || value > 255)
                    return false;
            }
            catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the private IP of the operating system.
     *
     * @return String that contains the private IP.
     * @throws IOException Occurs when was not possible to execute the operation.
     */
    public static String getPrivateIp() throws IOException{
        Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
        
        while(networkInterfaceEnumeration.hasMoreElements()){
            NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();
            Enumeration<InetAddress> networkInterfaceAddressesEnumeration = networkInterface.getInetAddresses();
            
            while(networkInterfaceAddressesEnumeration.hasMoreElements()){
                InetAddress networkInterfaceAddress = networkInterfaceAddressesEnumeration.nextElement();
                
                if(!networkInterfaceAddress.isLoopbackAddress() && networkInterfaceAddress.isSiteLocalAddress() && networkInterfaceAddress.getHostAddress() != null)
                    return networkInterfaceAddress.getHostAddress();
            }
        }
        
        return null;
    }
    
    /**
     * Indicates it is a loopback IP.
     *
     * @param address Instance that contains the IP.
     * @return True/False.
     */
    public static boolean isLoopbackAddress(InetAddress address){
        return (address != null && address.isLoopbackAddress());
    }
    
    /**
     * Indicates it is a private IP.
     *
     * @param address Instance that contains the IP.
     * @return True/False.
     */
    public static boolean isPrivateIp(InetAddress address){
        return (address != null && (isLoopbackAddress(address) || address.isSiteLocalAddress()));
    }
    
    /**
     * Indicates it is a loopback IP.
     *
     * @param address String that contains the IP.
     * @return True/False.
     * @throws IOException Occurs when was not possible to execute the
     * operation.
     */
    public static boolean isLoopbackAddress(String address) throws IOException{
        return (address != null && !address.isEmpty() && isLoopbackAddress(InetAddress.getByName(address)));
    }
    
    /**
     * Indicates it is a private IP.
     *
     * @param address String that contains the IP.
     * @return True/False.
     * @throws IOException Occurs when was not possible to execute the
     * operation.
     */
    public static boolean isPrivateIp(String address) throws IOException{
        if(address != null && !address.isEmpty())
            return isPrivateIp(InetAddress.getByName(address));
        
        return true;
    }
    
    /**
     * Indicates if the IP matches the network range.
     *
     * @param ip String that contains the IP.
     * @param expression String that contains the network range.
     * @return True/False.
     */
    public static boolean isIpMatches(String ip, String expression){
        if(ip == null || ip.isEmpty() || expression == null || expression.isEmpty())
            return false;
        
        if(expression.contains("/")){
            SubnetInfo info = new SubnetUtils(expression).getInfo();
            
            return (info.isInRange(ip));
        }
        
        StringBuilder regexBuffer = new StringBuilder();
        String[] octets = StringUtil.split(expression, ".");
        
        for(String octet: octets){
            if(!regexBuffer.isEmpty())
                regexBuffer.append(".");
            
            if(octet.contains("-")){
                String[] range = StringUtil.split(octet, "-");
                
                if(range.length == 2){
                    try{
                        int start = NumberUtil.parseInt(range[0]);
                        int end = NumberUtil.parseInt(range[1]);
                        
                        regexBuffer.append("(");
                        
                        for(int cont = start; cont <= end; cont++){
                            if(cont != start)
                                regexBuffer.append("|");
                            
                            regexBuffer.append(cont);
                        }
                        
                        regexBuffer.append(")");
                    }
                    catch(ParseException e){
                        regexBuffer.append(octet);
                    }
                }
                else
                    regexBuffer.append(octet);
            }
            else
                regexBuffer.append(octet);
        }
        
        String regex = StringUtil.replaceAll(regexBuffer.toString(), "*", "(25[0-4]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        
        return matcher.matches();
    }
}