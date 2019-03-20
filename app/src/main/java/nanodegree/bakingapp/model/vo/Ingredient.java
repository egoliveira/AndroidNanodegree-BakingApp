package nanodegree.bakingapp.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements RecipeOverviewItem, Parcelable {
    @JsonIgnore
    private int id;

    private float quantity;

    private String measure;

    private String ingredient;

    @JsonIgnore
    private int recipeId;

    public Ingredient() {
    }

    protected Ingredient(Parcel in) {
        this.id = in.readInt();
        this.quantity = in.readFloat();
        this.measure = in.readString();
        this.ingredient = in.readString();
        this.recipeId = in.readInt();
    }

    @JsonIgnore
    public int getId() {
        return id;
    }

    @JsonIgnore
    public void setId(int id) {
        this.id = id;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
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
        dest.writeInt(this.id);
        dest.writeFloat(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
        dest.writeInt(this.recipeId);
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
