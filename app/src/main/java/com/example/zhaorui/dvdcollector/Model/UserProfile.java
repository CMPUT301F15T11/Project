/*
 *
 *University of Alberta CMPUT 301 Group: CMPUT301F15T11
 *Copyright {2015} {Dingkai Liang, Zhaorui Chen, Jiaxuan Yue, Xi Zhang, Qingdai Du, Wei Song}
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *Unless required by applicable law or agreed to in writing,software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
*/
package com.example.zhaorui.dvdcollector.Model;

import java.util.Observable;

/**
 * <p>
 * The <code>UserProfile</code> class manages information for user's profile
 * by provide function to allowed other class to call to get and set user's profile information.
 * <p>
 *
 * @author  Dingkai Liang
 * @version 01/11/15
 * @see java.util.Observable
 */
public class UserProfile extends Observable{
    /**
     * initialize a string name to store user's name.
     */
    private String name;
    /**
     * initialize a string contact to store user's contract information.
     */
    private String contact;
    /**
     * initialize a string city to store user's city.
     */
    private String city;
    /**
     * This function is called when other function need to know the user name for the user profile.
     * @return a string variable name.
     */

    public String getName() {
        return name;
    }
    /**
     * This function is called when other function need to set the user name for the user profile.
     * @param  name, a string variable.
     */

    public void setName(String name) {this.name = name;}
    /**
     * This function is called when other function need to know the user's contact information for the user profile.
     * @return a string variable contains contact information.
     */

    public String getContact() {
        return contact;
    }
    /**
     * This function is called when other function need to set the user's contact information for the user profile.
     * @param  contact, a string variable.
     */

    public void setContact(String contact) {
        this.contact = contact;
        super.setChanged();
        super.notifyObservers();
    }
    /**
     * This function is called when other function need to know the user's city for the user profile.
     * @return a string variable contains the user's city.
     */

    public String getCity() {
        return city;
    }
    /**
     * This function is called when other function need to set the user's city for the user profile.
     * @param  city, a string variable.
     */
    public void setCity(String city) {
        this.city = city;
        super.setChanged();
        super.notifyObservers();
    }
}
