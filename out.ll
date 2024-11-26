; ModuleID = 'llvm-link'
source_filename = "llvm-link"
target datalayout = "e-m:e-p270:32:32-p271:32:32-p272:64:64-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-linux-gnu"

@.str.0 = private unnamed_addr constant [3 x i8] c"0\0A\00", align 1
@.str.1 = private unnamed_addr constant [2 x i8] c"\0A\00", align 1
@.str.2 = private unnamed_addr constant [10 x i8] c"19182620\0A\00", align 1
@.str = private unnamed_addr constant [3 x i8] c"%c\00", align 1
@.str.1.3 = private unnamed_addr constant [3 x i8] c"%d\00", align 1
@.str.2.6 = private unnamed_addr constant [4 x i8] c"%d:\00", align 1
@.str.3 = private unnamed_addr constant [4 x i8] c" %d\00", align 1
@.str.4 = private unnamed_addr constant [2 x i8] c"\0A\00", align 1
@.str.5 = private unnamed_addr constant [3 x i8] c"%s\00", align 1

define dso_local void @de() {
entry:
  ret void
}

define dso_local i32 @keke(i32 %0, i32 %1) {
entry:
  %2 = alloca i32, align 4
  store i32 %0, ptr %2, align 4
  %3 = alloca i32, align 4
  store i32 %1, ptr %3, align 4
  %4 = load i32, ptr %2, align 4
  %5 = load i32, ptr %3, align 4
  %6 = add i32 %4, %5
  store i32 %6, ptr %2, align 4
  ret i32 0
}

define dso_local i32 @jian() {
entry:
  %0 = alloca i32, align 4
  %1 = alloca i32, align 4
  %2 = alloca i32, align 4
  %3 = call i32 @getint()
  store i32 %3, ptr %0, align 4
  %4 = call i32 @getint()
  store i32 %4, ptr %1, align 4
  %5 = load i32, ptr %0, align 4
  %6 = load i32, ptr %1, align 4
  %7 = sub i32 %5, %6
  store i32 %7, ptr %2, align 4
  %8 = load i32, ptr %2, align 4
  ret i32 %8
}

define dso_local i32 @main() {
entry:
  %0 = alloca i32, align 4
  %1 = alloca i32, align 4
  %2 = alloca i32, align 4
  %3 = alloca i32, align 4
  %4 = alloca i32, align 4
  %5 = alloca i32, align 4
  %6 = alloca i32, align 4
  store i32 1, ptr %6, align 4
  %7 = alloca i32, align 4
  %8 = alloca i32, align 4
  %9 = alloca i32, align 4
  %10 = alloca i32, align 4
  %11 = alloca i32, align 4
  store i32 -1, ptr %11, align 4
  %12 = alloca i32, align 4
  store i32 2, ptr %12, align 4
  %13 = alloca i32, align 4
  %14 = alloca i32, align 4
  %15 = alloca i32, align 4
  store i32 0, ptr %15, align 4
  %16 = call i32 @getint()
  store i32 %16, ptr %13, align 4
  br label %Block_cond1

Block_cond1:                                      ; preds = %Block_update1, %entry
  %17 = load i32, ptr %12, align 4
  %18 = load i32, ptr %13, align 4
  %19 = icmp slt i32 %17, %18
  br i1 %19, label %Block_body0, label %Block_end1

Block_body0:                                      ; preds = %Block_cond1
  %20 = load i32, ptr %13, align 4
  %21 = load i32, ptr %12, align 4
  %22 = srem i32 %20, %21
  store i32 %22, ptr %14, align 4
  %23 = load i32, ptr %14, align 4
  %24 = icmp eq i32 %23, 0
  br i1 %24, label %Block_true0, label %Block_next1

Block_true0:                                      ; preds = %Block_body0
  store i32 1, ptr %15, align 4
  %25 = getelementptr inbounds [3 x i8], ptr @.str.0, i64 0, i64 0
  call void @putstr(ptr %25)
  br label %Block_next1

Block_next1:                                      ; preds = %Block_true0, %Block_body0
  %26 = load i32, ptr %12, align 4
  %27 = add i32 %26, 1
  store i32 %27, ptr %12, align 4
  br label %Block_update1

Block_update1:                                    ; preds = %Block_next1
  br label %Block_cond1

Block_end1:                                       ; preds = %Block_cond1
  %28 = call i32 @jian()
  store i32 %28, ptr %2, align 4
  %29 = load i32, ptr %2, align 4
  call void @putint(i32 %29)
  %30 = getelementptr inbounds [2 x i8], ptr @.str.1, i64 0, i64 0
  call void @putstr(ptr %30)
  %31 = load i32, ptr %2, align 4
  %32 = add i32 %31, 1
  store i32 %32, ptr %3, align 4
  %33 = load i32, ptr %2, align 4
  %34 = mul i32 %33, 2
  store i32 %34, ptr %4, align 4
  %35 = load i32, ptr %4, align 4
  %36 = icmp slt i32 %35, 5
  br i1 %36, label %Block_true1, label %Block_false2

Block_true1:                                      ; preds = %Block_end1
  %37 = load i32, ptr %2, align 4
  %38 = srem i32 %37, 2
  store i32 %38, ptr %5, align 4
  br label %Block_next2

Block_false2:                                     ; preds = %Block_end1
  %39 = load i32, ptr %2, align 4
  %40 = sdiv i32 %39, 2
  store i32 %40, ptr %5, align 4
  br label %Block_next2

Block_next2:                                      ; preds = %Block_false2, %Block_true1
  %41 = load i32, ptr %5, align 4
  %42 = icmp ne i32 %41, 0
  br i1 %42, label %Block_true2, label %Block_next3

Block_true2:                                      ; preds = %Block_next2
  %43 = load i32, ptr %6, align 4
  %44 = add i32 %43, 1
  store i32 %44, ptr %6, align 4
  br label %Block_next3

Block_next3:                                      ; preds = %Block_true2, %Block_next2
  %45 = load i32, ptr %12, align 4
  %46 = load i32, ptr %8, align 4
  %47 = add i32 %46, 1
  %48 = add i32 %45, %47
  store i32 %48, ptr %11, align 4
  br label %Block_cond2

Block_cond2:                                      ; preds = %Block_update2, %Block_body1, %Block_next3
  br label %Block_end2

Block_body1:                                      ; No predecessors!
  br label %Block_cond2

Block_continue0:                                  ; No predecessors!
  br label %Block_update2

Block_update2:                                    ; preds = %Block_continue0
  br label %Block_cond2

Block_end2:                                       ; preds = %Block_cond2
  br label %Block_cond3

Block_cond3:                                      ; preds = %Block_update3, %Block_end2
  br label %Block_body2

Block_body2:                                      ; preds = %Block_cond3
  br label %Block_end3

Block_break0:                                     ; No predecessors!
  br label %Block_update3

Block_update3:                                    ; preds = %Block_break0
  br label %Block_cond3

Block_end3:                                       ; preds = %Block_body2
  %49 = load i32, ptr %2, align 4
  %50 = load i32, ptr %3, align 4
  %51 = icmp eq i32 %49, %50
  br i1 %51, label %Block_true3, label %Block_next4

Block_true3:                                      ; preds = %Block_end3
  %52 = load i32, ptr %3, align 4
  %53 = load i32, ptr %4, align 4
  %54 = icmp sge i32 %52, %53
  br i1 %54, label %Block_true4, label %Block_next5

Block_true4:                                      ; preds = %Block_true3
  %55 = load i32, ptr %4, align 4
  %56 = load i32, ptr %5, align 4
  %57 = icmp sle i32 %55, %56
  br i1 %57, label %Block_true5, label %Block_next6

Block_true5:                                      ; preds = %Block_true4
  %58 = load i32, ptr %5, align 4
  %59 = load i32, ptr %6, align 4
  %60 = icmp ne i32 %58, %59
  br i1 %60, label %Block_true6, label %Block_next7

Block_true6:                                      ; preds = %Block_true5
  %61 = load i32, ptr %2, align 4
  %62 = icmp sgt i32 %61, 1
  br i1 %62, label %Block_true7, label %Block_next8

Block_true7:                                      ; preds = %Block_true6
  store i32 1, ptr %0, align 4
  br label %Block_next8

Block_next8:                                      ; preds = %Block_true7, %Block_true6
  br label %Block_next7

Block_next7:                                      ; preds = %Block_next8, %Block_true5
  br label %Block_next6

Block_next6:                                      ; preds = %Block_next7, %Block_true4
  br label %Block_next5

Block_next5:                                      ; preds = %Block_next6, %Block_true3
  br label %Block_next4

Block_next4:                                      ; preds = %Block_next5, %Block_end3
  %63 = load i32, ptr %0, align 4
  %64 = load i32, ptr %1, align 4
  %65 = call i32 @keke(i32 %63, i32 %64)
  %66 = load i32, ptr %3, align 4
  call void @putint(i32 %66)
  %67 = getelementptr inbounds [2 x i8], ptr @.str.1, i64 0, i64 0
  call void @putstr(ptr %67)
  %68 = load i32, ptr %4, align 4
  call void @putint(i32 %68)
  %69 = getelementptr inbounds [2 x i8], ptr @.str.1, i64 0, i64 0
  call void @putstr(ptr %69)
  %70 = load i32, ptr %5, align 4
  call void @putint(i32 %70)
  %71 = getelementptr inbounds [2 x i8], ptr @.str.1, i64 0, i64 0
  call void @putstr(ptr %71)
  %72 = load i32, ptr %6, align 4
  call void @putint(i32 %72)
  %73 = getelementptr inbounds [2 x i8], ptr @.str.1, i64 0, i64 0
  call void @putstr(ptr %73)
  %74 = getelementptr inbounds [10 x i8], ptr @.str.2, i64 0, i64 0
  call void @putstr(ptr %74)
  %75 = getelementptr inbounds [10 x i8], ptr @.str.2, i64 0, i64 0
  call void @putstr(ptr %75)
  %76 = getelementptr inbounds [10 x i8], ptr @.str.2, i64 0, i64 0
  call void @putstr(ptr %76)
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
  %2 = call i32 (ptr, ...) @__isoc99_scanf(ptr noundef @.str.1.3, ptr noundef %1)
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
  %5 = call i32 (ptr, ...) @__isoc99_scanf(ptr noundef @.str.1.3, ptr noundef %3)
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
  %15 = call i32 (ptr, ...) @__isoc99_scanf(ptr noundef @.str.1.3, ptr noundef %14)
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
  %4 = call i32 (ptr, ...) @printf(ptr noundef @.str.1.3, i32 noundef %3)
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
  %18 = call i32 (ptr, ...) @printf(ptr noundef @.str.3, i32 noundef %17)
  br label %19

19:                                               ; preds = %12
  %20 = load i32, ptr %5, align 4
  %21 = add nsw i32 %20, 1
  store i32 %21, ptr %5, align 4
  br label %8, !llvm.loop !9

22:                                               ; preds = %8
  %23 = call i32 (ptr, ...) @printf(ptr noundef @.str.4)
  ret void
}

; Function Attrs: noinline nounwind optnone uwtable
define dso_local void @putstr(ptr noundef %0) #0 {
  %2 = alloca ptr, align 8
  store ptr %0, ptr %2, align 8
  %3 = load ptr, ptr %2, align 8
  %4 = call i32 (ptr, ...) @printf(ptr noundef @.str.5, ptr noundef %3)
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
