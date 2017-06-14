package vs2.navjivanclient.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vs2.navjivanclient.R;


public class AboutFragment extends Fragment {

    private static final String tag = AboutFragment.class.getSimpleName();

    private View mView;

    CoordinatorLayout containerLayout;

    private TextView mTextFeedback;
    private TextView mTextEmail,mTextShareApp;
    private ImageView mImageVs2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment_about, container, false);
        initViews();

        return mView;
    }


    private void initViews() {
        containerLayout = (CoordinatorLayout) mView.findViewById(R.id.main_content);

        mTextFeedback = (TextView) mView.findViewById(R.id.text_feedback);
        mTextEmail = (TextView) mView.findViewById(R.id.text_email_friend);
        mImageVs2 = (ImageView) mView.findViewById(R.id.image_vs2);
        mTextShareApp = (TextView) mView.findViewById(R.id.text_share_app);

        mImageVs2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse("http://vs2.in/"));
                startActivity(browserIntent);
            }
        });

        mTextFeedback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                feedbackEmail();
            }
        });

        mTextEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                openEmailIntent("", "Check University Old Question Papers App");
            }
        });
        mTextShareApp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                shareAppLink();
            }
        });
    }

    private void shareAppLink() {
        // TODO Auto-generated method stub
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out this app at: https://play.google.com/store/apps/details?id=vs2.navjivanclient");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void feedbackEmail(){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ getString(R.string.feedback_email)});
        email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_subject));
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }
    private void openEmailIntent(String emailTo, String subject) {
        // TODO Auto-generated method stub
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", emailTo, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_link));
        startActivity(emailIntent);
    }


}