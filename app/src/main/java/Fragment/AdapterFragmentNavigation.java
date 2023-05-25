package Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class AdapterFragmentNavigation extends FragmentStatePagerAdapter {

    public AdapterFragmentNavigation(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new CartFragment();
            case 2:
                return new FavouriteFragment();
            case 3:
                return new SupportFragment();
            case 4:
                return new UserFragment();
        }
        return null;
    }


    @Override
    public int getCount() {
        return 5;
    }
}
