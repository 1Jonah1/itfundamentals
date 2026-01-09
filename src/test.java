import java.util.Scanner;

public class test {

    public static boolean isOdd(int number) {
        return (number & 1) == 1;
    }

    public static boolean isPowerOfTwo(int number) {
        if (number <= 0) return false;
        return (number & (number - 1)) == 0;
    }

    public static int twosComplement(int number) {
        return ~number + 1;
    }

    private static int[] parseIPv4(String s) {
        String[] parts = s.trim().split("\\.");
        if (parts.length != 4) throw new IllegalArgumentException("Invalid IPv4 format");
        int[] out = new int[4];
        for (int i = 0; i < 4; i++) {
            out[i] = Integer.parseInt(parts[i]);
            if (out[i] < 0 || out[i] > 255) throw new IllegalArgumentException("IPv4 number out of range");
        }
        return out;
    }

    private static String toBinary8(int n) {
        String b = Integer.toBinaryString(n);
        while (b.length() < 8) b = "0" + b;
        return b;
    }

    public static void calcNetworkSegment(String ipStr, String maskStr) {
        int[] ip = parseIPv4(ipStr);
        int[] mask = parseIPv4(maskStr);

        int[] net = new int[4];
        for (int i = 0; i < 4; i++) {
            net[i] = ip[i] & mask[i];
        }

        String ipBin = toBinary8(ip[0]) + "." + toBinary8(ip[1]) + "." + toBinary8(ip[2]) + "." + toBinary8(ip[3]);
        String maskBin = toBinary8(mask[0]) + "." + toBinary8(mask[1]) + "." + toBinary8(mask[2]) + "." + toBinary8(mask[3]);
        String netBin = toBinary8(net[0]) + "." + toBinary8(net[1]) + "." + toBinary8(net[2]) + "." + toBinary8(net[3]);

        String networkAddr = net[0] + "." + net[1] + "." + net[2] + "." + net[3];

        int blockSize = 256 - mask[3];
        int start = net[3];
        int end = start + (blockSize - 1);

        String rangeStart = net[0] + "." + net[1] + "." + net[2] + "." + start;
        String rangeEnd = net[0] + "." + net[1] + "." + net[2] + "." + end;

        System.out.println("IP Address:   " + ipBin);
        System.out.println("Subnet Mask:  " + maskBin);
        System.out.println("---------------------------------------------");
        System.out.println("Network Addr: " + netBin);
        System.out.println();
        System.out.println("Network address (decimal): " + networkAddr);
        System.out.println("Range: " + rangeStart + " to " + rangeEnd);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("==== Bit Calculations ====");
            System.out.println("1. Is number odd?");
            System.out.println("2. Is number a power of 2?");
            System.out.println("3. Two's complement of number");
            System.out.println("4. Calculate network segment (IP + subnet mask)");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = input.nextInt();
            input.nextLine(); // flush newline

            if (choice == 0) {
                running = false;
                System.out.println("Exiting program...");
            } else if (choice >= 1 && choice <= 3) {
                System.out.print("Enter an integer number: ");
                int number = input.nextInt();
                input.nextLine();

                if (choice == 1) {
                    if (isOdd(number)) System.out.println(number + " is odd");
                    else System.out.println(number + " is even");
                } else if (choice == 2) {
                    if (isPowerOfTwo(number)) System.out.println(number + " is a power of 2");
                    else System.out.println(number + " is NOT a power of 2");
                } else {
                    int result = twosComplement(number);
                    System.out.println("Two's complement of " + number + " = " + result);
                }
                System.out.println();
            } else if (choice == 4) {
                try {
                    System.out.print("Enter IP address (e.g. 192.168.1.100): ");
                    String ipStr = input.nextLine();

                    System.out.print("Enter subnet mask (e.g. 255.255.255.224): ");
                    String maskStr = input.nextLine();

                    System.out.println();
                    calcNetworkSegment(ipStr, maskStr);
                    System.out.println();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println();
                }
            } else {
                System.out.println("Unknown option, please try again.\n");
            }
        }

        input.close();
    }
}
