package ru.javaops.masterjava;

/**
 * User: gkislin
 * Date: 05.08.2015
 *
 * @link http://caloriesmng.herokuapp.com/
 * @link https://github.com/JavaOPs/topjava
 */
public class Main {
    public static void main(String[] args) {
        System.out.format("Hello MasterJava!");
        try {
            MainXml.printUsers("basejava");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
