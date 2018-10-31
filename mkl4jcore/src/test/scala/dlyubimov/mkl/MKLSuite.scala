package dlyubimov.mkl

import java.nio.{ByteBuffer, ByteOrder, DoubleBuffer, IntBuffer}

import dlyubimov.mkl.javacpp.MKL
import org.bytedeco.javacpp.{BytePointer, DoublePointer, IntPointer}
import org.scalatest.{FreeSpec, Matchers}

import scala.util.Random

/**
  * Created by dmitriy on 10/30/18.
  */
class MKLSuite extends FreeSpec with Matchers {

  import MKLSuite._


  "MKL" - {

    MKL.MKL_Set_Dynamic(1)
    MKL.MKL_Set_Interface_Layer(MKL.MKL_INTERFACE_LP64)
    MKL.MKL_Set_Threading_Layer(MKL.MKL_THREADING_GNU)
    //    MKL.MKL_Set_Num_Threads(2)

    "dgemm general" in {

      val m = 300
      val k = 231
      val n = 1289

      val rnd = new Random(134L)
      val arrA = DoubleBuffer.wrap(Array.tabulate(m * k)(_ ⇒ rnd.nextDouble()))
      val arrB = DoubleBuffer.wrap(Array.tabulate(k * n)(_ ⇒ rnd.nextDouble()))
      val arrC = DoubleBuffer.wrap(new Array[Double](m * n))

      MKL.dgemm(
        "N", "N",
        m, n, k,
        1.0,
        arrA, m,
        arrB, k,
        0d,
        arrC, m
      )

      for (i ← 0 until arrC.limit()) arrC.get(i) should not be 0.0
    }

    "dgemm jbuf no offset" in {
      val a = 5
      val b = 4

      val arrA = DoubleBuffer.allocate(30)
      val arrB = DoubleBuffer.allocate(30)
      val arrC = DoubleBuffer.allocate(30)

      arrA.put(0, a)
      arrB.put(0, b)

      MKL.dgemm(
        "N", "N",
        1, 1, 1,
        1.0,
        arrA, 1,
        arrB, 1,
        0d,
        arrC, 1
      )

      // Works
      arrC.get(0) should be(a * b)
    }

    "dgemm jbuf with offset by slice" in {
      val a = 5
      val b = 4

      val arrA = DoubleBuffer.allocate(30).position(6).asInstanceOf[DoubleBuffer].slice
      val arrB = DoubleBuffer.allocate(30).position(7).asInstanceOf[DoubleBuffer].slice
      val arrC = DoubleBuffer.allocate(30).position(9).asInstanceOf[DoubleBuffer].slice
      //      val arrC = DoubleBuffer.allocate(30)

      arrA.put(0, a)
      arrB.put(0, b)
      arrA.get should be (a)
      arrB.get should be (b)

      MKL.dgemm(
        "N", "N",
        1, 1, 1,
        1.0,
        arrA, 1,
        arrB, 1,
        0d,
        arrC, 1
      )

      // Fails
      arrC.get(0) should be(a * b)
    }

    "dgemm jbuf with offset by pointer" in {
      val a = 5
      val b = 4

      val arrA = DoubleBuffer.allocate(30).position(6).asInstanceOf[DoubleBuffer].slice
      val arrB = DoubleBuffer.allocate(30).position(7).asInstanceOf[DoubleBuffer].slice
      val arrC = DoubleBuffer.allocate(30).position(9).asInstanceOf[DoubleBuffer].slice

      arrA.put(0, a)
      arrB.put(0, b)

      MKL.dgemm(
        "N", "N",
        1, 1, 1,
        1.0,
        new DoublePointer(arrA), 1,
        new DoublePointer(arrB), 1,
        0d,
        new DoublePointer(arrC), 1
      )

      // Fail
      arrC.get(0) should be(a * b)
    }

    "dgemm jbuf with no offset by pointer" in {
      val a = 5
      val b = 4

      val arrA = DoubleBuffer.allocate(30)
      val arrB = DoubleBuffer.allocate(30)
      val arrC = DoubleBuffer.allocate(30)

      arrA.put(0, a)
      arrB.put(0, b)

      MKL.dgemm(
        "N", "N",
        1, 1, 1,
        1.0,
        new DoublePointer(arrA), 1,
        new DoublePointer(arrB), 1,
        0d,
        new DoublePointer(arrC), 1
      )

      // Fail
      arrC.get(0) should be(a * b)
    }

    "dgemm direct buf" in {
      val a = 5
      val b = 4

      val arrA = ByteBuffer.allocateDirect(80).order(ByteOrder.nativeOrder()).asDoubleBuffer()
      val arrB = ByteBuffer.allocateDirect(80).order(ByteOrder.nativeOrder()).asDoubleBuffer()
      val arrC = ByteBuffer.allocateDirect(80).order(ByteOrder.nativeOrder()).asDoubleBuffer()
      //      val arrC = DoubleBuffer.allocate(30)

      arrA.put(0, a)
      arrB.put(0, b)

      MKL.dgemm(
        "N", "N",
        1, 1, 1,
        1.0,
        arrA, 1,
        arrB, 1,
        0d,
        arrC, 1
      )

      arrC.get(0) should be(a * b)
    }

    "dgemm direct buf with offset by asDoubleBuffer" in {
      val a = 5
      val b = 4

      val arrA = ByteBuffer.allocateDirect(80).position(5).asInstanceOf[ByteBuffer]
        .order(ByteOrder.nativeOrder()).asDoubleBuffer()
      val arrB = ByteBuffer.allocateDirect(80).position(7).asInstanceOf[ByteBuffer]
        .order(ByteOrder.nativeOrder()).asDoubleBuffer()
      val arrC = ByteBuffer.allocateDirect(80).position(0).asInstanceOf[ByteBuffer]
        .order(ByteOrder.nativeOrder()).asDoubleBuffer()

      arrA.put(0, a)
      arrB.put(0, b)

      MKL.dgemm(
        "N", "N",
        1, 1, 1,
        1.0,
        arrA, 1,
        arrB, 1,
        0d,
        arrC, 1
      )

      arrC.get(0) should be(a * b)
    }

    "dgemm direct buf with offset by slice" in {
      val a = 5
      val b = 4

      val arrA = ByteBuffer.allocateDirect(80).order(ByteOrder.nativeOrder()).asDoubleBuffer()
        .position(5).asInstanceOf[DoubleBuffer].slice
      val arrB = ByteBuffer.allocateDirect(80).order(ByteOrder.nativeOrder()).asDoubleBuffer()
        .position(5).asInstanceOf[DoubleBuffer].slice
      val arrC = ByteBuffer.allocateDirect(80).order(ByteOrder.nativeOrder()).asDoubleBuffer()
        .position(5).asInstanceOf[DoubleBuffer].slice

      arrA.put(0, a)
      arrB.put(0, b)

      MKL.dgemm(
        "N", "N",
        1, 1, 1,
        1.0,
        arrA, 1,
        arrB, 1,
        0d,
        arrC, 1
      )

      arrC.get(0) should be(a * b)
    }

  }


}

object MKLSuite {

  implicit def i2ib(v: Int): IntBuffer = IntBuffer.allocate(1).put(0, v)

  implicit def d2db(v: Double): DoubleBuffer = DoubleBuffer.allocate(1).put(0, v)

  implicit def i2iptr(v: Int): IntPointer = new IntPointer(Array(v): _*)

  implicit def d2dptr(v: Double): DoublePointer = new DoublePointer(Array(v): _*)

}