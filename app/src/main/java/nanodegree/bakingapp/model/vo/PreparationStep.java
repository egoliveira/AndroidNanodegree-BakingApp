package nanodegree.bakingapp.model.vo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import android.os.Parcel;
import android.os.Parcelable;

public class PreparationStep implements RecipeOverviewItem, Parcelable {
    @JsonProperty("id")
    private int stepId;

    @JsonIgnore
    private int id;

    private String shortDescription;

    private String description;

    private String videoURL;

    private String thumbnailURL;

    @JsonIgnore
    private int recipeId;

    public PreparationStep() {
    }

    protected PreparationStep(Parcel in) {
        this.stepId = in.readInt();
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
        this.recipeId = in.readInt();
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @JsonIgnore
    public int getRecipeId() {
        return recipeId;
    }

    @JsonIgnore
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.stepId);
        dest.writeInt(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);
        dest.writeInt(this.recipeId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        PreparationStep that = (PreparationStep) o;

        return new EqualsBuilder().append(stepId, that.stepId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }

    public static final Parcelable.Creator<PreparationStep> CREATOR = new Parcelable.Creator<PreparationStep>() {
        @Override
        public PreparationStep createFromParcel(Parcel source) {
            return new PreparationStep(source);
        }

        @Override
        public PreparationStep[] newArray(int size) {
            return new PreparationStep[size];
        }
    };
}
