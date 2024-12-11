; ModuleID = 'llvm-link'
source_filename = "llvm-link"
target datalayout = "e-m:e-p270:32:32-p271:32:32-p272:64:64-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-linux-gnu"

@.str.0 = private unnamed_addr constant [31 x i8] c"Function with parameters: a = \00", align 1
@.str.1 = private unnamed_addr constant [7 x i8] c", b = \00", align 1
@.str.2 = private unnamed_addr constant [11 x i8] c" arr[0] = \00", align 1
@.str.3 = private unnamed_addr constant [12 x i8] c", str[0] = \00", align 1
@.str.4 = private unnamed_addr constant [2 x i8] c"\0A\00", align 1
@.str.5 = private unnamed_addr constant [25 x i8] c"Sum in func_with_param: \00", align 1
@.str.6 = private unnamed_addr constant [10 x i8] c"22373141\0A\00", align 1
@.str.7 = private unnamed_addr constant [23 x i8] c"Negative intArray[0]: \00", align 1
@.str.8 = private unnamed_addr constant [23 x i8] c"Positive intArray[0]: \00", align 1
@.str.9 = private unnamed_addr constant [11 x i8] c"Quotient: \00", align 1
@.str.10 = private unnamed_addr constant [14 x i8] c", Remainder: \00", align 1
@.str.11 = private unnamed_addr constant [22 x i8] c"Sum of ASCII codes1: \00", align 1
@.str.12 = private unnamed_addr constant [2 x i8] c" \00", align 1
@.str.13 = private unnamed_addr constant [22 x i8] c"Sum of ASCII codes2: \00", align 1
@.str.14 = private unnamed_addr constant [6 x i8] c"x1 = \00", align 1
@.str.15 = private unnamed_addr constant [6 x i8] c"a1 = \00", align 1
@.str.16 = private unnamed_addr constant [12 x i8] c", as char: \00", align 1
@constIntArray = dso_local constant [3 x i32] [i32 10, i32 20, i32 30]
@constCharArray = dso_local constant [5 x i8] c"ABCDE"
@constCharArray2 = dso_local constant [5 x i8] c"abc\00\00"
@intArray = dso_local global [5 x i32] zeroinitializer
@charArray = dso_local global [5 x i8] zeroinitializer
@.str = private unnamed_addr constant [3 x i8] c"%c\00", align 1
@.str.1.1 = private unnamed_addr constant [3 x i8] c"%d\00", align 1
@.str.2.6 = private unnamed_addr constant [4 x i8] c"%d:\00", align 1
@.str.3.7 = private unnamed_addr constant [4 x i8] c" %d\00", align 1
@.str.4.8 = private unnamed_addr constant [2 x i8] c"\0A\00", align 1
@.str.5.11 = private unnamed_addr constant [3 x i8] c"%s\00", align 1

define dso_local i32 @func_with_param(i32 %0, i8 %1, ptr %2, ptr %3) {
block0:
  %4 = alloca i32, align 4
  store i32 %0, ptr %4, align 4
  %5 = alloca i8, align 1
  store i8 %1, ptr %5, align 1
  %6 = alloca ptr, align 8
  store ptr %2, ptr %6, align 8
  %7 = alloca ptr, align 8
  store ptr %3, ptr %7, align 8
  %8 = getelementptr inbounds [31 x i8], ptr @.str.0, i64 0, i64 0
  call void @putstr(ptr %8)
  %9 = load i32, ptr %4, align 4
  call void @putint(i32 %9)
  %10 = getelementptr inbounds [7 x i8], ptr @.str.1, i64 0, i64 0
  call void @putstr(ptr %10)
  %11 = load i8, ptr %5, align 1
  %12 = zext i8 %11 to i32
  call void @putch(i32 %12)
  %13 = getelementptr inbounds [11 x i8], ptr @.str.2, i64 0, i64 0
  call void @putstr(ptr %13)
  %14 = load ptr, ptr %6, align 8
  %15 = getelementptr inbounds i32, ptr %14, i32 0
  %16 = load i32, ptr %15, align 4
  call void @putint(i32 %16)
  %17 = getelementptr inbounds [12 x i8], ptr @.str.3, i64 0, i64 0
  call void @putstr(ptr %17)
  %18 = load ptr, ptr %7, align 8
  %19 = getelementptr inbounds i8, ptr %18, i32 0
  %20 = load i8, ptr %19, align 1
  %21 = zext i8 %20 to i32
  call void @putch(i32 %21)
  %22 = getelementptr inbounds [2 x i8], ptr @.str.4, i64 0, i64 0
  call void @putstr(ptr %22)
  %23 = alloca i32, align 4
  %24 = load i32, ptr %4, align 4
  %25 = load i8, ptr %5, align 1
  %26 = zext i8 %25 to i32
  %27 = add i32 %24, %26
  %28 = load ptr, ptr %6, align 8
  %29 = getelementptr inbounds i32, ptr %28, i32 0
  %30 = load i32, ptr %29, align 4
  %31 = add i32 %27, %30
  %32 = load ptr, ptr %7, align 8
  %33 = getelementptr inbounds i8, ptr %32, i32 0
  %34 = load i8, ptr %33, align 1
  %35 = zext i8 %34 to i32
  %36 = add i32 %31, %35
  store i32 %36, ptr %23, align 4
  %37 = getelementptr inbounds [25 x i8], ptr @.str.5, i64 0, i64 0
  call void @putstr(ptr %37)
  %38 = load i32, ptr %23, align 4
  call void @putint(i32 %38)
  %39 = getelementptr inbounds [2 x i8], ptr @.str.4, i64 0, i64 0
  call void @putstr(ptr %39)
  %40 = load i32, ptr %23, align 4
  ret i32 %40
}

define dso_local i32 @main() {
block2:
  %0 = getelementptr inbounds [10 x i8], ptr @.str.6, i64 0, i64 0
  call void @putstr(ptr %0)
  %1 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 0
  %2 = getelementptr inbounds [3 x i32], ptr @constIntArray, i32 0, i32 0
  %3 = load i32, ptr %2, align 4
  store i32 %3, ptr %1, align 4
  %4 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 1
  %5 = getelementptr inbounds [3 x i32], ptr @constIntArray, i32 0, i32 1
  %6 = load i32, ptr %5, align 4
  store i32 %6, ptr %4, align 4
  %7 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 2
  %8 = getelementptr inbounds [3 x i32], ptr @constIntArray, i32 0, i32 2
  %9 = load i32, ptr %8, align 4
  store i32 %9, ptr %7, align 4
  %10 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 3
  %11 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 0
  %12 = load i32, ptr %11, align 4
  %13 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 1
  %14 = load i32, ptr %13, align 4
  %15 = add i32 %12, %14
  store i32 %15, ptr %10, align 4
  %16 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 4
  %17 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 3
  %18 = load i32, ptr %17, align 4
  %19 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 2
  %20 = load i32, ptr %19, align 4
  %21 = add i32 %18, %20
  store i32 %21, ptr %16, align 4
  %22 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 0
  %23 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 0
  %24 = load i32, ptr %23, align 4
  %25 = sub i32 0, %24
  store i32 %25, ptr %22, align 4
  %26 = getelementptr inbounds [23 x i8], ptr @.str.7, i64 0, i64 0
  call void @putstr(ptr %26)
  %27 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 0
  %28 = load i32, ptr %27, align 4
  call void @putint(i32 %28)
  %29 = getelementptr inbounds [2 x i8], ptr @.str.4, i64 0, i64 0
  call void @putstr(ptr %29)
  %30 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 0
  %31 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 0
  %32 = load i32, ptr %31, align 4
  %33 = load i32, ptr %31, align 4
  store i32 %33, ptr %30, align 4
  %34 = getelementptr inbounds [23 x i8], ptr @.str.8, i64 0, i64 0
  call void @putstr(ptr %34)
  %35 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 0
  %36 = load i32, ptr %35, align 4
  call void @putint(i32 %36)
  %37 = getelementptr inbounds [2 x i8], ptr @.str.4, i64 0, i64 0
  call void @putstr(ptr %37)
  %38 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 1
  %39 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 3
  %40 = load i32, ptr %39, align 4
  %41 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 2
  %42 = load i32, ptr %41, align 4
  %43 = sdiv i32 %40, %42
  store i32 %43, ptr %38, align 4
  %44 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 2
  %45 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 3
  %46 = load i32, ptr %45, align 4
  %47 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 2
  %48 = load i32, ptr %47, align 4
  %49 = srem i32 %46, %48
  store i32 %49, ptr %44, align 4
  %50 = getelementptr inbounds [11 x i8], ptr @.str.9, i64 0, i64 0
  call void @putstr(ptr %50)
  %51 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 1
  %52 = load i32, ptr %51, align 4
  call void @putint(i32 %52)
  %53 = getelementptr inbounds [14 x i8], ptr @.str.10, i64 0, i64 0
  call void @putstr(ptr %53)
  %54 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 2
  %55 = load i32, ptr %54, align 4
  call void @putint(i32 %55)
  %56 = getelementptr inbounds [2 x i8], ptr @.str.4, i64 0, i64 0
  call void @putstr(ptr %56)
  %57 = getelementptr inbounds [5 x i8], ptr @charArray, i32 0, i32 0
  %58 = getelementptr inbounds [5 x i8], ptr @constCharArray, i32 0, i32 0
  %59 = load i8, ptr %58, align 1
  %60 = zext i8 %59 to i32
  %61 = getelementptr inbounds [5 x i8], ptr @constCharArray, i32 0, i32 1
  %62 = load i8, ptr %61, align 1
  %63 = zext i8 %62 to i32
  %64 = add i32 %60, %63
  %65 = getelementptr inbounds [5 x i8], ptr @constCharArray, i32 0, i32 2
  %66 = load i8, ptr %65, align 1
  %67 = zext i8 %66 to i32
  %68 = add i32 %64, %67
  %69 = getelementptr inbounds [5 x i8], ptr @constCharArray, i32 0, i32 3
  %70 = load i8, ptr %69, align 1
  %71 = zext i8 %70 to i32
  %72 = add i32 %68, %71
  %73 = getelementptr inbounds [5 x i8], ptr @constCharArray, i32 0, i32 4
  %74 = load i8, ptr %73, align 1
  %75 = zext i8 %74 to i32
  %76 = add i32 %72, %75
  %77 = srem i32 %76, 128
  %78 = trunc i32 %77 to i8
  store i8 %78, ptr %57, align 1
  %79 = getelementptr inbounds [22 x i8], ptr @.str.11, i64 0, i64 0
  call void @putstr(ptr %79)
  %80 = getelementptr inbounds [5 x i8], ptr @charArray, i32 0, i32 0
  %81 = load i8, ptr %80, align 1
  %82 = zext i8 %81 to i32
  call void @putint(i32 %82)
  %83 = getelementptr inbounds [2 x i8], ptr @.str.12, i64 0, i64 0
  call void @putstr(ptr %83)
  %84 = getelementptr inbounds [5 x i8], ptr @charArray, i32 0, i32 0
  %85 = load i8, ptr %84, align 1
  %86 = zext i8 %85 to i32
  call void @putch(i32 %86)
  %87 = getelementptr inbounds [2 x i8], ptr @.str.4, i64 0, i64 0
  call void @putstr(ptr %87)
  %88 = alloca i32, align 4
  %89 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 0
  %90 = load i32, ptr %89, align 4
  %91 = getelementptr inbounds [5 x i8], ptr @charArray, i32 0, i32 0
  %92 = load i8, ptr %91, align 1
  %93 = getelementptr inbounds [5 x i32], ptr @intArray, i32 0, i32 0
  %94 = getelementptr inbounds [5 x i8], ptr @charArray, i32 0, i32 0
  %95 = call i32 @func_with_param(i32 %90, i8 %92, ptr %93, ptr %94)
  store i32 %95, ptr %88, align 4
  %96 = alloca i32, align 4
  %97 = getelementptr inbounds [5 x i8], ptr @constCharArray2, i32 0, i32 0
  %98 = load i8, ptr %97, align 1
  %99 = zext i8 %98 to i32
  %100 = getelementptr inbounds [5 x i8], ptr @constCharArray2, i32 0, i32 1
  %101 = load i8, ptr %100, align 1
  %102 = zext i8 %101 to i32
  %103 = add i32 %99, %102
  %104 = getelementptr inbounds [5 x i8], ptr @constCharArray2, i32 0, i32 2
  %105 = load i8, ptr %104, align 1
  %106 = zext i8 %105 to i32
  %107 = add i32 %103, %106
  %108 = getelementptr inbounds [5 x i8], ptr @constCharArray2, i32 0, i32 3
  %109 = load i8, ptr %108, align 1
  %110 = zext i8 %109 to i32
  %111 = add i32 %107, %110
  %112 = getelementptr inbounds [5 x i8], ptr @constCharArray2, i32 0, i32 4
  %113 = load i8, ptr %112, align 1
  %114 = zext i8 %113 to i32
  %115 = add i32 %111, %114
  store i32 %115, ptr %96, align 4
  %116 = alloca i8, align 1
  %117 = getelementptr inbounds [5 x i8], ptr @constCharArray2, i32 0, i32 0
  %118 = load i8, ptr %117, align 1
  %119 = zext i8 %118 to i32
  %120 = getelementptr inbounds [5 x i8], ptr @constCharArray2, i32 0, i32 1
  %121 = load i8, ptr %120, align 1
  %122 = zext i8 %121 to i32
  %123 = add i32 %119, %122
  %124 = getelementptr inbounds [5 x i8], ptr @constCharArray2, i32 0, i32 2
  %125 = load i8, ptr %124, align 1
  %126 = zext i8 %125 to i32
  %127 = add i32 %123, %126
  %128 = getelementptr inbounds [5 x i8], ptr @constCharArray2, i32 0, i32 3
  %129 = load i8, ptr %128, align 1
  %130 = zext i8 %129 to i32
  %131 = add i32 %127, %130
  %132 = getelementptr inbounds [5 x i8], ptr @constCharArray2, i32 0, i32 4
  %133 = load i8, ptr %132, align 1
  %134 = zext i8 %133 to i32
  %135 = add i32 %131, %134
  %136 = srem i32 %135, 128
  %137 = trunc i32 %136 to i8
  store i8 %137, ptr %116, align 1
  %138 = getelementptr inbounds [22 x i8], ptr @.str.13, i64 0, i64 0
  call void @putstr(ptr %138)
  %139 = load i32, ptr %96, align 4
  call void @putint(i32 %139)
  %140 = getelementptr inbounds [2 x i8], ptr @.str.12, i64 0, i64 0
  call void @putstr(ptr %140)
  %141 = load i8, ptr %116, align 1
  %142 = zext i8 %141 to i32
  call void @putch(i32 %142)
  %143 = getelementptr inbounds [2 x i8], ptr @.str.4, i64 0, i64 0
  call void @putstr(ptr %143)
  %144 = alloca i32, align 4
  store i32 107, ptr %144, align 4
  %145 = getelementptr inbounds [6 x i8], ptr @.str.14, i64 0, i64 0
  call void @putstr(ptr %145)
  %146 = load i32, ptr %144, align 4
  call void @putint(i32 %146)
  %147 = getelementptr inbounds [2 x i8], ptr @.str.4, i64 0, i64 0
  call void @putstr(ptr %147)
  %148 = alloca i8, align 1
  store i8 41, ptr %148, align 1
  %149 = getelementptr inbounds [6 x i8], ptr @.str.15, i64 0, i64 0
  call void @putstr(ptr %149)
  %150 = load i8, ptr %148, align 1
  %151 = zext i8 %150 to i32
  call void @putint(i32 %151)
  %152 = getelementptr inbounds [12 x i8], ptr @.str.16, i64 0, i64 0
  call void @putstr(ptr %152)
  %153 = load i8, ptr %148, align 1
  %154 = zext i8 %153 to i32
  call void @putch(i32 %154)
  %155 = getelementptr inbounds [2 x i8], ptr @.str.4, i64 0, i64 0
  call void @putstr(ptr %155)
  ret i32 0
}

; Function Attrs: noinline nounwind optnone uwtable
define dso_local i32 @getchar() #0 {
  %1 = alloca i8, align 1
  %2 = call i32 (ptr, ...) @__isoc99_scanf(ptr noundef @.str, ptr noundef %1)
  %3 = load i8, ptr %1, align 1
  %4 = sext i8 %3 to i32
  ret i32 %4
}

declare i32 @__isoc99_scanf(ptr noundef, ...) #1

; Function Attrs: noinline nounwind optnone uwtable
define dso_local i32 @getint() #0 {
  %1 = alloca i32, align 4
  %2 = call i32 (ptr, ...) @__isoc99_scanf(ptr noundef @.str.1.1, ptr noundef %1)
  br label %3

3:                                                ; preds = %6, %0
  %4 = call i32 @getchar()
  %5 = icmp ne i32 %4, 10
  br i1 %5, label %6, label %7

6:                                                ; preds = %3
  br label %3, !llvm.loop !6

7:                                                ; preds = %3
  %8 = load i32, ptr %1, align 4
  ret i32 %8
}

; Function Attrs: noinline nounwind optnone uwtable
define dso_local i32 @getarray(ptr noundef %0) #0 {
  %2 = alloca ptr, align 8
  %3 = alloca i32, align 4
  %4 = alloca i32, align 4
  store ptr %0, ptr %2, align 8
  %5 = call i32 (ptr, ...) @__isoc99_scanf(ptr noundef @.str.1.1, ptr noundef %3)
  store i32 0, ptr %4, align 4
  br label %6

6:                                                ; preds = %16, %1
  %7 = load i32, ptr %4, align 4
  %8 = load i32, ptr %3, align 4
  %9 = icmp slt i32 %7, %8
  br i1 %9, label %10, label %19

10:                                               ; preds = %6
  %11 = load ptr, ptr %2, align 8
  %12 = load i32, ptr %4, align 4
  %13 = sext i32 %12 to i64
  %14 = getelementptr inbounds i32, ptr %11, i64 %13
  %15 = call i32 (ptr, ...) @__isoc99_scanf(ptr noundef @.str.1.1, ptr noundef %14)
  br label %16

16:                                               ; preds = %10
  %17 = load i32, ptr %4, align 4
  %18 = add nsw i32 %17, 1
  store i32 %18, ptr %4, align 4
  br label %6, !llvm.loop !8

19:                                               ; preds = %6
  %20 = load i32, ptr %3, align 4
  ret i32 %20
}

; Function Attrs: noinline nounwind optnone uwtable
define dso_local void @putint(i32 noundef %0) #0 {
  %2 = alloca i32, align 4
  store i32 %0, ptr %2, align 4
  %3 = load i32, ptr %2, align 4
  %4 = call i32 (ptr, ...) @printf(ptr noundef @.str.1.1, i32 noundef %3)
  ret void
}

declare i32 @printf(ptr noundef, ...) #1

; Function Attrs: noinline nounwind optnone uwtable
define dso_local void @putch(i32 noundef %0) #0 {
  %2 = alloca i32, align 4
  store i32 %0, ptr %2, align 4
  %3 = load i32, ptr %2, align 4
  %4 = call i32 (ptr, ...) @printf(ptr noundef @.str, i32 noundef %3)
  ret void
}

; Function Attrs: noinline nounwind optnone uwtable
define dso_local void @putarray(i32 noundef %0, ptr noundef %1) #0 {
  %3 = alloca i32, align 4
  %4 = alloca ptr, align 8
  %5 = alloca i32, align 4
  store i32 %0, ptr %3, align 4
  store ptr %1, ptr %4, align 8
  %6 = load i32, ptr %3, align 4
  %7 = call i32 (ptr, ...) @printf(ptr noundef @.str.2.6, i32 noundef %6)
  store i32 0, ptr %5, align 4
  br label %8

8:                                                ; preds = %19, %2
  %9 = load i32, ptr %5, align 4
  %10 = load i32, ptr %3, align 4
  %11 = icmp slt i32 %9, %10
  br i1 %11, label %12, label %22

12:                                               ; preds = %8
  %13 = load ptr, ptr %4, align 8
  %14 = load i32, ptr %5, align 4
  %15 = sext i32 %14 to i64
  %16 = getelementptr inbounds i32, ptr %13, i64 %15
  %17 = load i32, ptr %16, align 4
  %18 = call i32 (ptr, ...) @printf(ptr noundef @.str.3.7, i32 noundef %17)
  br label %19

19:                                               ; preds = %12
  %20 = load i32, ptr %5, align 4
  %21 = add nsw i32 %20, 1
  store i32 %21, ptr %5, align 4
  br label %8, !llvm.loop !9

22:                                               ; preds = %8
  %23 = call i32 (ptr, ...) @printf(ptr noundef @.str.4.8)
  ret void
}

; Function Attrs: noinline nounwind optnone uwtable
define dso_local void @putstr(ptr noundef %0) #0 {
  %2 = alloca ptr, align 8
  store ptr %0, ptr %2, align 8
  %3 = load ptr, ptr %2, align 8
  %4 = call i32 (ptr, ...) @printf(ptr noundef @.str.5.11, ptr noundef %3)
  ret void
}

attributes #0 = { noinline nounwind optnone uwtable "frame-pointer"="all" "min-legal-vector-width"="0" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #1 = { "frame-pointer"="all" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }

!llvm.ident = !{!0}
!llvm.module.flags = !{!1, !2, !3, !4, !5}

!0 = !{!"Ubuntu clang version 14.0.0-1ubuntu1.1"}
!1 = !{i32 1, !"wchar_size", i32 4}
!2 = !{i32 8, !"PIC Level", i32 2}
!3 = !{i32 7, !"PIE Level", i32 2}
!4 = !{i32 7, !"uwtable", i32 1}
!5 = !{i32 7, !"frame-pointer", i32 2}
!6 = distinct !{!6, !7}
!7 = !{!"llvm.loop.mustprogress"}
!8 = distinct !{!8, !7}
!9 = distinct !{!9, !7}
