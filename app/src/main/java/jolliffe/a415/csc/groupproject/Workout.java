package jolliffe.a415.csc.groupproject;

/**
 * Created by jacob on 4/11/2017.
 */

public class Workout {

    private int reps;
    private int sets;
    private float weight;
    private int workoutId;
    private String workoutType;

    public String getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(String workoutDate) {
        this.workoutDate = workoutDate;
    }

    private String workoutDate;

    public Workout(){

    }

    public Workout(int reps, int sets, float weight, int workoutId, String workoutType) {
        this.reps = reps;
        this.sets = sets;
        this.weight = weight;
        this.workoutId = workoutId;
        this.workoutType = workoutType;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(String workoutType) {
        this.workoutType = workoutType;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "reps=" + reps +
                ", sets=" + sets +
                ", weight=" + weight +
                ", workoutId=" + workoutId +
                ", workoutType='" + workoutType + '\'' +
                '}';
    }
}
