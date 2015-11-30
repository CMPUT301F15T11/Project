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

import android.app.Activity;
import android.app.Application;
import android.app.FragmentManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.zhaorui.dvdcollector.Controller.DataManager;
import com.example.zhaorui.dvdcollector.Controller.TradeListController;
import com.example.zhaorui.dvdcollector.View.InputInvalidDialog;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <p>
 * The <code>ContextUtil</code> controls the content for loading from and writing to local files.
 * <p>
 *
 * @author  Dingkai Liang
 * @version 03/11/15
 * */
public class ContextUtil extends Application{
    /**
     * Initialize a static content
     */
    private static ContextUtil instance;
    /**
     * This function is to get the content for loading and writing to local files
     * @return instance
     */
    public static ContextUtil getInstance() {
        return instance;
    }

    public void onCreate(){
        super.onCreate();
        instance = this;
    }

    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }
}

