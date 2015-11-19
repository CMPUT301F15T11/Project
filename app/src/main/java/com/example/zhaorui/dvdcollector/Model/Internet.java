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
 * The <code>Internet</code> class manages the internet connection of the app.
 * <p>
 *
 * @author  Dingkai Liang
 * @version 09/10/15
 */
public class Internet {
    private boolean connecting;
    private static Internet instance;

    private Internet() {
    }

    public static Internet getInstance() {
        if (instance==null) instance = new Internet();
        return instance;
    }

    public boolean isConnecting() {
        return connecting;
    }

    public void disconnect(){}

    public void connect(){}

    public void downloadData(){}
}
