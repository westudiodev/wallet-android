package com.learningmachine.android.app.data.bitcoin;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.bitcoinj.params.TestNet3Params;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BitcoinManagerTest {

    @Test
    public void walletShouldBeSaved_andLoaded() {
        Context context = InstrumentationRegistry.getTargetContext();
        TestNet3Params networkParameters = TestNet3Params.get();

        StringHolder stringHolder = new StringHolder();
        BitcoinManager firstBitcoinManager = new BitcoinManager(context, networkParameters, null);
        firstBitcoinManager.getPassphrase().subscribe(firstPassphrase -> {
            assertTrue(firstBitcoinManager.getWalletFile().exists());
            assertThat(firstPassphrase, not(isEmptyOrNullString()));
            stringHolder.string = firstPassphrase;
        });

        BitcoinManager secondBitcoinManager = new BitcoinManager(context, networkParameters, null);
        secondBitcoinManager.getPassphrase().subscribe(secondPassphrase -> {
            assertTrue(secondBitcoinManager.getWalletFile().exists());
            assertEquals(stringHolder.string, secondPassphrase);
        });
    }

    static class StringHolder {
        String string;
    }
}
