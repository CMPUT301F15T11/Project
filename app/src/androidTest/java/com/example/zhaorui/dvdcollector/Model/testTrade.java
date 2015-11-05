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

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

/**
 * Created by dingkai on 15/10/9.
 */
public class testTrade extends ActivityInstrumentationTestCase2 {
    public testTrade(Class activityClass) {
        super(activityClass);
    }

    public void testOfferTrade()throws Exception{
        User borrower = new User();
        User owner = new User();
        borrower.friends().add(owner);
        new Trade(borrower,owner);
        assertTrue(borrower.getTradeList().get(1).getOwner() == owner);
    }

    public void testNotifyTrade()throws Exception{
        User borrower = new User();
        User owner = new User();
        borrower.friends().add(owner);
        new Trade(borrower,owner);
        assertTrue(owner.isNotify());
    }

    public void testDecidTrade()throws Exception{
        User borrower = new User();
        User owner = new User();
        borrower.friends().add(owner);
        new Trade(borrower,owner);
        owner.getTradeList().get(1).decide(false);
        assertFalse(borrower.getTradeList().get(1).getStatus());
    }

    public void testCounterTrade()throws Exception{
        User borrower = new User();
        User owner = new User();
        borrower.friends().add(owner);
        new Trade(borrower,owner);
        owner.getTradeList().get(1).decide(false);
        owner.getTradeList().get(1).counterTrade();
        assertFalse(borrower.getTradeList().get(2).getOwner()==borrower);
    }

    public void testEditTrade()throws Exception{
        User borrower = new User();
        User owner = new User();
        borrower.friends().add(owner);
        new Trade(borrower,owner);
        DVD dvd = new DVD();
        borrower.getTradeList().get(1).itemList.add(dvd);
        assertTrue(owner.getTradeList().get(1).itemList.get(1) == dvd);
    }

    public void testDeleteTrade()throws Exception{
        User borrower = new User();
        User owner = new User();
        borrower.friends().add(owner);
        new Trade(borrower,owner);
        borrower.getTradeList().remove(1);
        assertTrue(owner.getTradeList().size() == 0);
    }

    public void testEmailTrady()throws Exception{
        User borrower = new User();
        User owner = new User();
        borrower.friends().add(owner);
        new Trade(borrower,owner);
        borrower.getTradeList().get(1).email();
        assertFalse(borrower.getTradeList().get(1).email());
    }

    public void testBrowseOneWayTrade()throws Exception{
        User borrower = new User();
        User owner = new User();
        borrower.friends().add(owner);
        new Trade(borrower,owner);
        assertTrue(borrower.browseTrade("owner").size() == 0);
    }

    public void testBrowseAllTrade() throws Exception{
        User borrower = new User();
        User owner = new User();
        borrower.friends().add(owner);
        new Trade(borrower,owner);
        new Trade(owner,borrower);
        assertTrue(borrower.browseTrade("all").size()==2);
    }
}