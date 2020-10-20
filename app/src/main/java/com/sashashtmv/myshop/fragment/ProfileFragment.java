package com.sashashtmv.myshop.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sashashtmv.myshop.R;
import com.sashashtmv.myshop.helper.NetworkConnection;

import java.io.IOException;

public class ProfileFragment extends Fragment {

    private static final int CHOOSE_IMAGE = 101;
    private TextView textView, textViewEmail;
    private ImageView imageView;
    private EditText editText;
    private Button save;
    private Uri uriProfileImage;
    private ProgressBar progressBar;
    private String profileimageUrl;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_profile, container, false);


        mAuth = FirebaseAuth.getInstance();
        editText = (EditText) v.findViewById(R.id.editTextDisplayName);
        imageView = (ImageView) v.findViewById(R.id.imageView);
        progressBar = v.findViewById(R.id.progressbar);
        textView = v.findViewById(R.id.textViewVerified);
        textViewEmail = v.findViewById(R.id.text_view_email);
        save = v.findViewById(R.id.buttonSave);
        if (mAuth.getCurrentUser() != null)
            textViewEmail.setText(mAuth.getCurrentUser().getEmail());


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });
        loadUserInformation();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() != null)
                    saveUserInformation();
                else
                    Toast.makeText(getActivity(), R.string.permit_authorisation, Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getInt("seller") == 1) {
            Toast.makeText(getActivity(), R.string.permit_profile, Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void loadUserInformation() {
        NetworkConnection networkConnection = new NetworkConnection();
        if (networkConnection.isConnectedToInternet(getActivity())
                || networkConnection.isConnectedToMobileNetwork(getActivity())
                || networkConnection.isConnectedToWifi(getActivity())) {

        } else {
            networkConnection.showNoInternetAvailableErrorDialog(getActivity());
            return;
        }
        final FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            if (user.getPhotoUrl() != null) {
                String photoUrl = user.getPhotoUrl().toString();
                Glide.with(this)
                        .load(photoUrl)
                        .into(imageView);
            }

            if (user.getDisplayName() != null) {
                String displayName = user.getDisplayName();
                editText.setText(displayName);
            }

            if (user.isEmailVerified()) {
                textView.setText(R.string.verified);
            } else {
                textView.setText(R.string.email_not_verified);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textView.setTextColor(1);
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), R.string.email_sent, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        }
    }


    private void saveUserInformation() {
        NetworkConnection networkConnection = new NetworkConnection();
        if (networkConnection.isConnectedToInternet(getActivity())
                || networkConnection.isConnectedToMobileNetwork(getActivity())
                || networkConnection.isConnectedToWifi(getActivity())) {

        } else {
            networkConnection.showNoInternetAvailableErrorDialog(getActivity());
            return;
        }
        String displayName = editText.getText().toString();
        if (displayName.isEmpty()) {
            editText.setError(getString(R.string.name_required));
            editText.requestFocus();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();

        if (profileimageUrl == null && imageView.getDrawable() == null) {
            Toast.makeText(getActivity(), R.string.image_not_selected, Toast.LENGTH_SHORT).show();
            return;
        }


        if (user != null && profileimageUrl != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profileimageUrl))
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), R.string.updated_successfully, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    });

        } else {
            Toast.makeText(getActivity(), R.string.error_occured, Toast.LENGTH_LONG).show();
            return;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriProfileImage);
                imageView.setImageBitmap(bitmap);
                uploadImageToFirebaseStorage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {
        final StorageReference ProfileImageRef;
        ProfileImageRef = FirebaseStorage.getInstance().getReference(mAuth.getCurrentUser().getEmail() + ".jpg");

        if (uriProfileImage != null) {
            progressBar.setVisibility(View.VISIBLE);

            ProfileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            ProfileImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    profileimageUrl = uri.toString();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }


    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_image)), CHOOSE_IMAGE);
    }

}
