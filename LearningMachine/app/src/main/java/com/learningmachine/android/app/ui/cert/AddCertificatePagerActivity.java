package com.learningmachine.android.app.ui.cert;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;

import com.learningmachine.android.app.R;
import com.learningmachine.android.app.databinding.ActivityAddCertificateBinding;
import com.learningmachine.android.app.ui.LMActivity;

import java.util.ArrayList;
import java.util.List;


public class AddCertificatePagerActivity extends LMActivity {

    private ActivityAddCertificateBinding mBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, AddCertificatePagerActivity.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_certificate);
        setSupportActionBar(mBinding.addCertificateToolbar);

        setupViewPager(mBinding.activityCertificatePagerViewPager);
        mBinding.addCertificateTabs.setupWithViewPager(mBinding.activityCertificatePagerViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        List<CertificateType> certificateTypes = new ArrayList<>();
        certificateTypes.add(CertificateType.URL);
        certificateTypes.add(CertificateType.FILE);

        AddCertificateViewPagerAdapter adapter = new AddCertificateViewPagerAdapter(this, getSupportFragmentManager(), certificateTypes);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fragment_add_certificate, menu);
        return true;
    }

    @Override
    protected boolean requiresBackNavigation() {
        return true;
    }

    private enum CertificateType {
        URL(R.string.add_certificate_by_url),
        FILE(R.string.add_certificate_by_file);

        private int mTitleResId;

        CertificateType(int titleResId) {
            mTitleResId = titleResId;
        }

        public int getTitleResId() {
            return mTitleResId;
        }

        public Fragment createFragment() {
            switch (this) {
                case URL:
                    return AddCertificateURLFragment.newInstance();
                case FILE:
                    return AddCertificateFileFragment.newInstance();
            }
            return null;
        }
    }

    private class AddCertificateViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<CertificateType> mCertificateTypes;
        private Context mContext;

        public AddCertificateViewPagerAdapter(Context context, FragmentManager manager, List<CertificateType> certificateTypes) {
            super(manager);
            mContext = context;
            mCertificateTypes = certificateTypes;
        }

        @Override
        public Fragment getItem(int position) {
            CertificateType certificateType = mCertificateTypes.get(position);
            return certificateType.createFragment();
        }

        @Override
        public int getCount() {
            return mCertificateTypes.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            CertificateType certificateType = mCertificateTypes.get(position);
            return mContext.getString(certificateType.getTitleResId());
        }
    }
}
