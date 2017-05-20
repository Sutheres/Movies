    package com.example.sutheres.movies;

    import android.content.Context;
    import android.provider.ContactsContract;
    import android.support.v7.widget.RecyclerView;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.bumptech.glide.Glide;

    import java.util.ArrayList;
    import java.util.List;

    import static java.security.AccessController.getContext;

    /**
     * Created by Sutheres on 2/21/2017.
     */


    public class MovieAdapter extends ArrayAdapter<Movie> {


        private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w185";

        public MovieAdapter(Context context, ArrayList<Movie> movies) {
            // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
            // the second argument is used when the ArrayAdapter is populating a single TextView.
            // Because this is a custom adapter, the adapter is not
            // going to use this second argument, so it can be any value. Here, we used 0.
            super(context, 0, movies);
        }

        /**
         *
         * @param position The position in the list of data that should be displayed in the list item view.
         * @param convertView The recycled view to populate.
         * @param parent The parent ViewGroup that is used for inflation.
         * @return the View for the position in the AdapterView.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Check if the existing view is being resued, otherwise inflate the view
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            // Get the (@link Movie) object located at this position in the list
            Movie currentMovie = getItem(position);

            // Find the ImageView in the list_item.xml layout with the ID image_view
            ImageView posterIV = (ImageView) listItemView.findViewById(R.id.poster);
            //Toast.makeText(getContext(), currentMovie.getTitle() , Toast.LENGTH_SHORT).show();
            Glide.with(getContext()).load(BASE_IMAGE_URL + currentMovie.getPosterPath()).into(posterIV);
            return listItemView;
        }
    }

