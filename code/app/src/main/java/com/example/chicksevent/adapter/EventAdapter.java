package com.example.chicksevent.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chicksevent.R;
import com.example.chicksevent.misc.Event;
import com.example.chicksevent.misc.FirebaseService;
import com.example.chicksevent.util.DateFormatter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Custom {@link EventAdapter} subclass for displaying events
 */
public class EventAdapter extends ArrayAdapter<Event> {
    private static final String TAG = EventAdapter.class.getSimpleName();
    /** Listener interface for responding to item button clicks. */
    OnItemButtonClickListener listener;
    FirebaseService imageService = new FirebaseService("Image");

    private final HashMap<String, String> imageCache = new HashMap<>();
    
    /**
     * ViewHolder pattern to cache view references and avoid repeated findViewById() calls.
     */
    static class ViewHolder {
        ImageView posterImageView;
        TextView status;
        TextView eventName;
        TextView startTime;
        TextView endTime;
        TextView date;
        ImageButton arrowButton;
        String eventId; // Track which event this view belongs to
    }


    /**
     * Interface defining a callback when a button inside a list item is clicked.
     */
    public interface OnItemButtonClickListener {
        /**
         * Invoked when a button associated with a given {@link Event} item is clicked.
         *
         * @param item the {@link Event} whose button was clicked.
         */
        void onItemButtonClick(Event item);
    }

    /**
     * Constructs a new {@code EventAdapter} for displaying events.
     *
     * @param context the activity or fragment context.
     * @param eventArray list of events to display.
     * @param listener callback interface to handle per-item button clicks.
     */
    public EventAdapter(Context context, ArrayList<Event> eventArray, OnItemButtonClickListener listener) {
        super(context, 0, eventArray);
        this.listener = listener;
    }

    /**
     * Provides a view for an adapter view (ListView, GridView, etc.) based on the event data.
     * Inflates {@code item_event.xml} if necessary and binds data to its views.
     *
     * @param position the position of the item within the dataset.
     * @param convertView the old view to reuse, if possible.
     * @param parent the parent view that this view will be attached to.
     * @return the view corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_event, parent, false);
            holder = new ViewHolder();
            // Cache all view references to avoid repeated findViewById() calls
            holder.posterImageView = view.findViewById(R.id.img_event);
            holder.status = view.findViewById(R.id.tv_status);
            holder.eventName = view.findViewById(R.id.tv_event_name);
            holder.startTime = view.findViewById(R.id.tv_startTime);
            holder.endTime = view.findViewById(R.id.tv_endTime);
            holder.date = view.findViewById(R.id.tv_date);
            holder.arrowButton = view.findViewById(R.id.btn_arrow);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        Event event = getItem(position);
        if (event == null) {
            return view;
        }

        holder.posterImageView.setImageResource(R.drawable.sample_image);
        holder.eventId = event.getId();

        if (imageCache.containsKey(event.getId())) {
            Glide.with(holder.posterImageView.getContext())
                    .load(imageCache.get(event.getId()))
                    .into(holder.posterImageView);
        } else {


            imageService.getReference()
                    .child(event.getId())
                    .child("poster")
                    .get()
                    .addOnSuccessListener(snapshot -> {
                        if (!event.getId().equals(holder.eventId)) return;

                        String imageUrl = snapshot.getValue(String.class);
                        if (imageUrl == null || imageUrl.isEmpty()) {
                            return;
                        }

                        // Cache the URL for future use
                        imageCache.put(event.getId(), imageUrl);

                        Glide.with(holder.posterImageView.getContext())
                                .load(imageUrl)
                                .placeholder(R.drawable.sample_image)
                                .error(R.drawable.sample_image)
                                .into(holder.posterImageView);
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Failed to load image for event: " + event.getId(), e);
                        // Keep the placeholder image that was already set
                    });
        }



        // ----- Format tv_date as "MMM\ndd" using DateFormatter utility -----
        String startDateStr = event.getEventStartDate(); // e.g., "03-15-2025"

        if (startDateStr != null && !startDateStr.isEmpty()) {
            String[] monthDay = DateFormatter.parseDateToMonthDay(startDateStr);
            if (monthDay != null && monthDay.length == 2) {
                String display = monthDay[0] + "\n" + monthDay[1]; // "MAR\n15"
                holder.date.setText(display);
            } else {
                holder.date.setText(startDateStr); // fallback
            }
        } else {
            holder.date.setText(""); // or "N/A"
        }





        holder.eventName.setText(event.getName());
        holder.startTime.setText(event.getEventStartTime());
        holder.endTime.setText(event.getEventEndTime());

        holder.arrowButton.setOnClickListener(l -> {
            if (listener != null) listener.onItemButtonClick(event);
        });
        return view;
    }
}