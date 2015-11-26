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

/**
 * <p>
 * The <code>Configuration</code> manages the config information of the app.
 * <p>
 *
 * @author  Dingkai Liang
 * @version 09/10/15
 */
public class Configuration {
    /**
     * Initialize a boolean variable to store whether use confirms request
     */
    private boolean confirm;

    /**
     * Configure user's profile
     */
    public Configuration() {
    }

    /**
     * This function is called when oher functions need to set whether user confirm trade or not
     * @param confirm a boolean variable
     */
    public Configuration(boolean confirm) {
        this.confirm = confirm;
    }

    /**
     * This function is to return whether the user confirms request or not
     * @return  boolean value of whether confirm or not
     */
    public boolean isConfirm() {
        return confirm;
    }
}
