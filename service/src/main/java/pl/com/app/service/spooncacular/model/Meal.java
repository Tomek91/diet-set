package pl.com.app.service.spooncacular.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Meal {
    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("image")
    private String image;
    @SerializedName("imageUrls")
    private List<String> imageUrls = null;
    @SerializedName("readyInMinutes")
    private Integer readyInMinutes;
    @SerializedName("servings")
    private Integer servings;
}
