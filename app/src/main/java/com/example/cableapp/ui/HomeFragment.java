package com.example.cableapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cableapp.R;
import com.example.cableapp.databinding.FragmentHomeBinding;
import com.example.cableapp.viewModel.HomeViewModel;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        FragmentHomeBinding binding = FragmentHomeBinding.bind(rootView);
        binding.setMyHomeViewModel(homeViewModel);
        binding.setLifecycleOwner(this);

        Bundle args = getArguments();
        String email;
        if(args != null){
            email = args.getString("email");
        } else {
            email = "Tidak Terkirim";
        }
        Log.i("EMAIL_HOME", email);
        homeViewModel.getUserData(email);
        homeViewModel.getUsername().observe(getViewLifecycleOwner(), hasUsername ->{
            if(hasUsername != null){
                String username = hasUsername;
                binding.textView.setText("Selamat Datang \n"+username);
            }
        });

        homeViewModel.getErrotoast().observe(getViewLifecycleOwner(), hasError -> {
            if(hasError == 1){
                Toast.makeText(requireContext(), "Input harus diisi", Toast.LENGTH_SHORT).show();
                homeViewModel.donetoast();
            }
        });

        homeViewModel.getSuccesstoast().observe(getViewLifecycleOwner(), hasData ->{
            if(hasData == 1){
                Toast.makeText(requireContext(), "Berhasil Menambahkan Data", Toast.LENGTH_SHORT).show();
                homeViewModel.donetoast();
            }
        });

        homeViewModel.getNavigatetoLogin().observe(getViewLifecycleOwner(), hasFinished -> {
            if (hasFinished != null && hasFinished) {
                Log.i("MYTAG", "inside observe");
                logout();
                homeViewModel.doneLogout();
            }
        });

        return rootView;
    }

    private void logout() {
        Log.i("MYTAG", "logout");
        NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_loginFragment);
    }
}
