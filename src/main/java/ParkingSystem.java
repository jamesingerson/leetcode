class ParkingSystem {
    // https://leetcode.com/problems/design-parking-system/
    private int[] parkingSpaces;

    public ParkingSystem(int big, int medium, int small) {
        this.parkingSpaces = new int[]{big, medium, small};
    }

    public boolean addCar(int carType) {
        if (this.parkingSpaces[carType - 1] > 0) {
            this.parkingSpaces[carType - 1] -= 1;
            return true;
        }
        return false;
    }
}