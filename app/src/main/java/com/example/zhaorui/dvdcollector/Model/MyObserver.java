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
import java.util.Observer;
/**
 * <p>
 * The <code>MyObserver</code> is a observer to make sure everything updates in the interface on time.
 * <p>
 *
 * @author  Zhaorui Chen
 * @version 4/11/15
 * @see java.util.Observer
 */

public interface MyObserver extends Observer{
    void update(Observable o, Object arg);
}
