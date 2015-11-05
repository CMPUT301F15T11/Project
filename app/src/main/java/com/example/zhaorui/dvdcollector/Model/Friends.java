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

import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * <p>
 * The <code>Friends</code> class manages observers to make sure the friend list can be update on time on user's interface.
 * <p>
 *
 * @author  Dingkai Liang
 * @version 04/11/15
 */
public class Friends extends ArrayList<String>{
    private Obs obs;

    public Friends(){obs = new Obs();}

    private class Obs extends Observable{
        public void notifying(){
            super.setChanged();
            super.notifyObservers();
        }
    }
    public void notifying(){obs.notifying();}
    public Observable getObs() {
        return obs;
    }
}
