.data
.align 2
.globl _int_tag
.globl _string_tag
.globl _bool_tag
.globl bool_const0
.globl bool_const1
.globl class_nameTab
.globl Object_protObj
.globl IO_protObj
.globl Int_protObj
.globl String_protObj
.globl Bool_protObj
.globl List_protObj
.globl A2I_protObj
.globl Main_protObj

_int_tag:
    .word 4
_string_tag:
    .word 5
_bool_tag:
    .word 6

int_const0:
    .word 4
    .word 4
    .word Int_dispTab
    .word 0
str_const0:
    .word 5
    .word 4
    .word String_dispTab
    .word int_const0
    .asciiz ""
    .align 2
bool_const0:
    .word 6
    .word 4
    .word Bool_dispTab
    .word 0
bool_const1:
    .word 6
    .word 4
    .word Bool_dispTab
    .word 1
int_const1:
    .word 4
    .word 4
    .word Int_dispTab
    .word 6
str_const1:
    .word 5
    .word 6
    .word String_dispTab
    .word int_const1
    .asciiz "Object"
    .align 2
int_const2:
    .word 4
    .word 4
    .word Int_dispTab
    .word 2
str_const2:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const2
    .asciiz "IO"
    .align 2
int_const3:
    .word 4
    .word 4
    .word Int_dispTab
    .word 3
str_const3:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const3
    .asciiz "Int"
    .align 2
str_const4:
    .word 5
    .word 6
    .word String_dispTab
    .word int_const1
    .asciiz "String"
    .align 2
int_const4:
    .word 4
    .word 4
    .word Int_dispTab
    .word 4
str_const5:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const4
    .asciiz "Bool"
    .align 2
str_const6:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const4
    .asciiz "List"
    .align 2
int_const5:
    .word 4
    .word 4
    .word Int_dispTab
    .word 9
str_const7:
    .word 5
    .word 7
    .word String_dispTab
    .word int_const5
    .asciiz "32-big.cl"
    .align 2
str_const8:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const3
    .asciiz "A2I"
    .align 2
int_const6:
    .word 4
    .word 4
    .word Int_dispTab
    .word 1
str_const9:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const6
    .asciiz " "
    .align 2
str_const10:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const6
    .asciiz "
"
    .align 2
str_const11:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const4
    .asciiz "Main"
    .align 2
str_const12:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const6
    .asciiz "!"
    .align 2
int_const7:
    .word 4
    .word 4
    .word Int_dispTab
    .word 27
str_const13:
    .word 5
    .word 11
    .word String_dispTab
    .word int_const7
    .asciiz "Calculam factorial pentru: "
    .align 2
int_const8:
    .word 4
    .word 4
    .word Int_dispTab
    .word 20
str_const14:
    .word 5
    .word 9
    .word String_dispTab
    .word int_const8
    .asciiz "Factorial recursiv: "
    .align 2
str_const15:
    .word 5
    .word 9
    .word String_dispTab
    .word int_const8
    .asciiz "Factorial iterativ: "
    .align 2
str_const16:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const6
    .asciiz "0"
    .align 2
str_const17:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const6
    .asciiz "1"
    .align 2
str_const18:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const6
    .asciiz "2"
    .align 2
str_const19:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const6
    .asciiz "3"
    .align 2
str_const20:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const6
    .asciiz "4"
    .align 2
str_const21:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const6
    .asciiz "5"
    .align 2
int_const9:
    .word 4
    .word 4
    .word Int_dispTab
    .word 5
str_const22:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const6
    .asciiz "6"
    .align 2
str_const23:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const6
    .asciiz "7"
    .align 2
int_const10:
    .word 4
    .word 4
    .word Int_dispTab
    .word 7
str_const24:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const6
    .asciiz "8"
    .align 2
int_const11:
    .word 4
    .word 4
    .word Int_dispTab
    .word 8
str_const25:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const6
    .asciiz "9"
    .align 2
str_const26:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const6
    .asciiz "-"
    .align 2
str_const27:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const6
    .asciiz "+"
    .align 2
int_const12:
    .word 4
    .word 4
    .word Int_dispTab
    .word 10

class_nameTab:
    .word str_const1
    .word str_const2
    .word str_const6
    .word str_const11
    .word str_const3
    .word str_const4
    .word str_const5
    .word str_const8

class_objTab:
    .word Object_protObj
    .word Object_init
    .word IO_protObj
    .word IO_init
    .word List_protObj
    .word List_init
    .word Main_protObj
    .word Main_init
    .word Int_protObj
    .word Int_init
    .word String_protObj
    .word String_init
    .word Bool_protObj
    .word Bool_init
    .word A2I_protObj
    .word A2I_init

Object_protObj:
    .word 0
    .word 3
    .word Object_dispTab
IO_protObj:
    .word 1
    .word 3
    .word IO_dispTab
Int_protObj:
    .word 4
    .word 4
    .word Int_dispTab
    .word 0
String_protObj:
    .word 5
    .word 5
    .word String_dispTab
    .word int_const0
    .asciiz ""
Bool_protObj:
    .word 6
    .word 4
    .word Bool_dispTab
    .word bool_const0
List_protObj:
    .word 2
    .word 5
    .word List_dispTab
    .word 0
    .word 0
A2I_protObj:
    .word 7
    .word 3
    .word A2I_dispTab
Main_protObj:
    .word 3
    .word 3
    .word Main_dispTab

Object_dispTab:
    .word Object.abort
    .word Object.type_name
    .word Object.copy
IO_dispTab:
    .word Object.abort
    .word Object.type_name
    .word Object.copy
    .word IO.out_string
    .word IO.out_int
    .word IO.in_string
    .word IO.in_int
Int_dispTab:
    .word Object.abort
    .word Object.type_name
    .word Object.copy
String_dispTab:
    .word Object.abort
    .word Object.type_name
    .word Object.copy
    .word String.length
    .word String.concat
    .word String.substr
Bool_dispTab:
    .word Object.abort
    .word Object.type_name
    .word Object.copy
List_dispTab:
    .word Object.abort
    .word Object.type_name
    .word Object.copy
    .word IO.out_string
    .word IO.out_int
    .word IO.in_string
    .word IO.in_int
    .word List.init
    .word List.print
A2I_dispTab:
    .word Object.abort
    .word Object.type_name
    .word Object.copy
    .word A2I.c2i
    .word A2I.i2c
    .word A2I.a2i
    .word A2I.a2i_aux
    .word A2I.i2a
    .word A2I.i2a_aux
Main_dispTab:
    .word Object.abort
    .word Object.type_name
    .word Object.copy
    .word IO.out_string
    .word IO.out_int
    .word IO.in_string
    .word IO.in_int
    .word Main.main
    .word Main.fact_rec
    .word Main.fact_iter

    .globl heap_start
heap_start:
    .word 0

.text
Object_init:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    move $a0 $s0
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    jr $ra
IO_init:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    jal Object_init
    move $a0 $s0
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    jr $ra
Int_init:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    jal Object_init
    move $a0 $s0
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    jr $ra
String_init:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    jal Object_init
    move $a0 $s0
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    jr $ra
Bool_init:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    jal Object_init
    move $a0 $s0
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    jr $ra
List_init:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    jal IO_init
    move $a0 $s0
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    jr $ra
List.init:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    lw $a0 12($fp)
    sw $a0 12($s0)
    lw $a0 16($fp)
    sw $a0 16($s0)
	move $a0 $s0
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    addiu $sp $sp 8
    jr $ra
A2I_init:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    jal Object_init
    move $a0 $s0
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    jr $ra
List.print:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    addiu $sp $sp -4
    addiu $sp $sp -4 # locals alloc
    lw $a0 12($s0)
    sw $a0 -4($fp)
    bnez $a0 case0
    la $a0 str_const7
    li $t1 24
    jal _case_abort2
case0:
    sw $a0 -4($fp)
    lw $t1 0($a0) # class tag
    blt $t1 5 casebranch2
    bgt $t1 5 casebranch2
    lw $a0 -4($fp)
    b endcase0

casebranch2:
    blt $t1 4 casebranch3
    bgt $t1 4 casebranch3
    lw $a0 -4($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 A2I_protObj
    jal Object.copy
    jal A2I_init
    bnez $a0 dispatch0
    la $a0 str_const7
    li $t1 26
    jal _dispatch_abort
dispatch0:
    lw $t1 8($a0) # dispatch table
    lw $t1 28($t1) # method offset
    jalr $t1
    b endcase0

casebranch3:
    blt $t1 0 casebranch4
    bgt $t1 7 casebranch4
	move $a0 $s0
    bnez $a0 dispatch1
    la $a0 str_const7
    li $t1 27
    jal _dispatch_abort
dispatch1:
    lw $t1 8($a0) # dispatch table
    lw $t1 0($t1) # method offset
    jalr $t1
    la $a0 str_const0
    b endcase0
casebranch4:
    lw $a0 -4($fp)
    jal _case_abort
endcase0:
    addiu $sp $sp 4 # locals free
    sw $a0 -4($fp)
    la $a0 str_const9
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 -4($fp)
    bnez $a0 dispatch2
    la $a0 str_const7
    li $t1 31
    jal _dispatch_abort
dispatch2:
    lw $t1 8($a0) # dispatch table
    lw $t1 16($t1) # method offset
    jalr $t1
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch3
    la $a0 str_const7
    li $t1 31
    jal _dispatch_abort
dispatch3:
    lw $t1 8($a0) # dispatch table
    lw $t1 12($t1) # method offset
    jalr $t1
    lw $a0 16($s0)
    move $t1 $a0
    la $a0 bool_const1
    beqz $t1 isvoid5
    la $a0 bool_const0
isvoid5:
    lw $t1 12($a0) # bool slot
    beqz $t1 else6
    la $a0 str_const10
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch4
    la $a0 str_const7
    li $t1 32
    jal _dispatch_abort
dispatch4:
    lw $t1 8($a0) # dispatch table
    lw $t1 12($t1) # method offset
    jalr $t1
    b endif6
else6:
    lw $a0 16($s0)
    bnez $a0 dispatch5
    la $a0 str_const7
    li $t1 32
    jal _dispatch_abort
dispatch5:
    lw $t1 8($a0) # dispatch table
    lw $t1 32($t1) # method offset
    jalr $t1
endif6:
    addiu $sp $sp 4
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    jr $ra
Main_init:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    jal IO_init
    move $a0 $s0
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    jr $ra
Main.main:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    addiu $sp $sp -20
    la $a0 int_const0
    sw $a0 -4($fp)
    la $a0 str_const12
    sw $a0 -8($fp)
    lw $a0 -4($fp)
	sw $a0 0($sp)
	addiu $sp $sp -4
    la $a0 int_const2
    jal Object.copy
	lw $t1 4($sp)
	lw $t1 12($t1) # int slot
    lw $t2 12($a0) # int slot
	add $t1 $t1 $t2
    sw $t1 12($a0) # int slot
	addiu $sp $sp 4
    sw $a0 -12($fp)
	li $a0 0
    sw $a0 -16($fp)
    lw $a0 -16($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 -12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 List_protObj
    jal Object.copy
    jal List_init
    bnez $a0 dispatch6
    la $a0 str_const7
    li $t1 47
    jal _dispatch_abort
dispatch6:
    lw $t1 8($a0) # dispatch table
    lw $t1 28($t1) # method offset
    jalr $t1
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 -8($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 List_protObj
    jal Object.copy
    jal List_init
    bnez $a0 dispatch7
    la $a0 str_const7
    li $t1 46
    jal _dispatch_abort
dispatch7:
    lw $t1 8($a0) # dispatch table
    lw $t1 28($t1) # method offset
    jalr $t1
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 -4($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 List_protObj
    jal Object.copy
    jal List_init
    bnez $a0 dispatch8
    la $a0 str_const7
    li $t1 45
    jal _dispatch_abort
dispatch8:
    lw $t1 8($a0) # dispatch table
    lw $t1 28($t1) # method offset
    jalr $t1
    sw $a0 -20($fp)

    lw $a0 -20($fp)
    bnez $a0 dispatch9
    la $a0 str_const7
    li $t1 49
    jal _dispatch_abort
dispatch9:
    lw $t1 8($a0) # dispatch table
    lw $t1 32($t1) # method offset
    jalr $t1
    addiu $sp $sp 20
    addiu $sp $sp -4

    la $a0 str_const13
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch10
    la $a0 str_const7
    li $t1 52
    jal _dispatch_abort
dispatch10:
    lw $t1 8($a0) # dispatch table
    lw $t1 12($t1) # method offset
    jalr $t1
    bnez $a0 dispatch11
    la $a0 str_const7
    li $t1 52
    jal _dispatch_abort
dispatch11:
    lw $t1 8($a0) # dispatch table
    lw $t1 24($t1) # method offset
    jalr $t1
    sw $a0 -4($fp)
    la $a0 str_const10
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 -4($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch12
    la $a0 str_const7
    li $t1 55
    jal _dispatch_abort
dispatch12:
    lw $t1 8($a0) # dispatch table
    lw $t1 32($t1) # method offset
    jalr $t1
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 str_const14
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch13
    la $a0 str_const7
    li $t1 55
    jal _dispatch_abort
dispatch13:
    lw $t1 8($a0) # dispatch table
    lw $t1 12($t1) # method offset
    jalr $t1
    bnez $a0 dispatch14
    la $a0 str_const7
    li $t1 55
    jal _dispatch_abort
dispatch14:
    lw $t1 8($a0) # dispatch table
    lw $t1 16($t1) # method offset
    jalr $t1
    bnez $a0 dispatch15
    la $a0 str_const7
    li $t1 55
    jal _dispatch_abort
dispatch15:
    lw $t1 8($a0) # dispatch table
    lw $t1 12($t1) # method offset
    jalr $t1
    la $a0 str_const10
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 -4($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch16
    la $a0 str_const7
    li $t1 57
    jal _dispatch_abort
dispatch16:
    lw $t1 8($a0) # dispatch table
    lw $t1 36($t1) # method offset
    jalr $t1
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 str_const15
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch17
    la $a0 str_const7
    li $t1 57
    jal _dispatch_abort
dispatch17:
    lw $t1 8($a0) # dispatch table
    lw $t1 12($t1) # method offset
    jalr $t1
    bnez $a0 dispatch18
    la $a0 str_const7
    li $t1 57
    jal _dispatch_abort
dispatch18:
    lw $t1 8($a0) # dispatch table
    lw $t1 16($t1) # method offset
    jalr $t1
    bnez $a0 dispatch19
    la $a0 str_const7
    li $t1 57
    jal _dispatch_abort
dispatch19:
    lw $t1 8($a0) # dispatch table
    lw $t1 12($t1) # method offset
    jalr $t1
    addiu $sp $sp 4
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    jr $ra
Main.fact_rec:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const0
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq7
    la $a1 bool_const0
    jal equality_test
eq7:
    lw $t1 12($a0) # bool slot
    beqz $t1 else8
    la $a0 int_const6
    b endif8
else8:
    lw $a0 12($fp)
	sw $a0 0($sp)
	addiu $sp $sp -4
    lw $a0 12($fp)
	sw $a0 0($sp)
	addiu $sp $sp -4
    la $a0 int_const6
    jal Object.copy
	lw $t1 4($sp)
	lw $t1 12($t1) # int slot
    lw $t2 12($a0) # int slot
	sub $t1 $t1 $t2
    sw $t1 12($a0) # int slot
	addiu $sp $sp 4
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch20
    la $a0 str_const7
    li $t1 65
    jal _dispatch_abort
dispatch20:
    lw $t1 8($a0) # dispatch table
    lw $t1 32($t1) # method offset
    jalr $t1
    jal Object.copy
	lw $t1 4($sp)
	lw $t1 12($t1) # int slot
    lw $t2 12($a0) # int slot
	mul $t1 $t1 $t2
    sw $t1 12($a0) # int slot
	addiu $sp $sp 4
endif8:
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    addiu $sp $sp 4
    jr $ra
Main.fact_iter:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    addiu $sp $sp -4
    la $a0 int_const6
    sw $a0 -4($fp)
while11:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const0
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq9
    la $a1 bool_const0
    jal equality_test
eq9:
    lw $t1 12($a0) # bool slot
    la $a0 bool_const1
    beqz $t1 not10
    la $a0 bool_const0
not10:
    lw $t1 12($a0) # bool slot
    beqz $t1 endwhile11
    lw $a0 -4($fp)
	sw $a0 0($sp)
	addiu $sp $sp -4
    lw $a0 12($fp)
    jal Object.copy
	lw $t1 4($sp)
	lw $t1 12($t1) # int slot
    lw $t2 12($a0) # int slot
	mul $t1 $t1 $t2
    sw $t1 12($a0) # int slot
	addiu $sp $sp 4
    sw $a0 -4($fp)
    lw $a0 12($fp)
	sw $a0 0($sp)
	addiu $sp $sp -4
    la $a0 int_const6
    jal Object.copy
	lw $t1 4($sp)
	lw $t1 12($t1) # int slot
    lw $t2 12($a0) # int slot
	sub $t1 $t1 $t2
    sw $t1 12($a0) # int slot
	addiu $sp $sp 4
    sw $a0 12($fp)
    b while11
endwhile11:
    move $a0 $zero
    lw $a0 -4($fp)
    addiu $sp $sp 4
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    addiu $sp $sp 4
    jr $ra
A2I.c2i:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 str_const16
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq12
    la $a1 bool_const0
    jal equality_test
eq12:
    lw $t1 12($a0) # bool slot
    beqz $t1 else31
    la $a0 int_const0
    b endif31
else31:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 str_const17
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq13
    la $a1 bool_const0
    jal equality_test
eq13:
    lw $t1 12($a0) # bool slot
    beqz $t1 else30
    la $a0 int_const6
    b endif30
else30:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 str_const18
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq14
    la $a1 bool_const0
    jal equality_test
eq14:
    lw $t1 12($a0) # bool slot
    beqz $t1 else29
    la $a0 int_const2
    b endif29
else29:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 str_const19
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq15
    la $a1 bool_const0
    jal equality_test
eq15:
    lw $t1 12($a0) # bool slot
    beqz $t1 else28
    la $a0 int_const3
    b endif28
else28:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 str_const20
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq16
    la $a1 bool_const0
    jal equality_test
eq16:
    lw $t1 12($a0) # bool slot
    beqz $t1 else27
    la $a0 int_const4
    b endif27
else27:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 str_const21
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq17
    la $a1 bool_const0
    jal equality_test
eq17:
    lw $t1 12($a0) # bool slot
    beqz $t1 else26
    la $a0 int_const9
    b endif26
else26:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 str_const22
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq18
    la $a1 bool_const0
    jal equality_test
eq18:
    lw $t1 12($a0) # bool slot
    beqz $t1 else25
    la $a0 int_const1
    b endif25
else25:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 str_const23
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq19
    la $a1 bool_const0
    jal equality_test
eq19:
    lw $t1 12($a0) # bool slot
    beqz $t1 else24
    la $a0 int_const10
    b endif24
else24:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 str_const24
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq20
    la $a1 bool_const0
    jal equality_test
eq20:
    lw $t1 12($a0) # bool slot
    beqz $t1 else23
    la $a0 int_const11
    b endif23
else23:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 str_const25
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq21
    la $a1 bool_const0
    jal equality_test
eq21:
    lw $t1 12($a0) # bool slot
    beqz $t1 else22
    la $a0 int_const5
    b endif22
else22:
	move $a0 $s0
    bnez $a0 dispatch21
    la $a0 str_const7
    li $t1 111
    jal _dispatch_abort
dispatch21:
    lw $t1 8($a0) # dispatch table
    lw $t1 0($t1) # method offset
    jalr $t1
    la $a0 int_const0
endif22:
endif23:
endif24:
endif25:
endif26:
endif27:
endif28:
endif29:
endif30:
endif31:
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    addiu $sp $sp 4
    jr $ra
A2I.i2c:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const0
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq32
    la $a1 bool_const0
    jal equality_test
eq32:
    lw $t1 12($a0) # bool slot
    beqz $t1 else51
    la $a0 str_const16
    b endif51
else51:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const6
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq33
    la $a1 bool_const0
    jal equality_test
eq33:
    lw $t1 12($a0) # bool slot
    beqz $t1 else50
    la $a0 str_const17
    b endif50
else50:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const2
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq34
    la $a1 bool_const0
    jal equality_test
eq34:
    lw $t1 12($a0) # bool slot
    beqz $t1 else49
    la $a0 str_const18
    b endif49
else49:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const3
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq35
    la $a1 bool_const0
    jal equality_test
eq35:
    lw $t1 12($a0) # bool slot
    beqz $t1 else48
    la $a0 str_const19
    b endif48
else48:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const4
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq36
    la $a1 bool_const0
    jal equality_test
eq36:
    lw $t1 12($a0) # bool slot
    beqz $t1 else47
    la $a0 str_const20
    b endif47
else47:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const9
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq37
    la $a1 bool_const0
    jal equality_test
eq37:
    lw $t1 12($a0) # bool slot
    beqz $t1 else46
    la $a0 str_const21
    b endif46
else46:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const1
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq38
    la $a1 bool_const0
    jal equality_test
eq38:
    lw $t1 12($a0) # bool slot
    beqz $t1 else45
    la $a0 str_const22
    b endif45
else45:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const10
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq39
    la $a1 bool_const0
    jal equality_test
eq39:
    lw $t1 12($a0) # bool slot
    beqz $t1 else44
    la $a0 str_const23
    b endif44
else44:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const11
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq40
    la $a1 bool_const0
    jal equality_test
eq40:
    lw $t1 12($a0) # bool slot
    beqz $t1 else43
    la $a0 str_const24
    b endif43
else43:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const5
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq41
    la $a1 bool_const0
    jal equality_test
eq41:
    lw $t1 12($a0) # bool slot
    beqz $t1 else42
    la $a0 str_const25
    b endif42
else42:
	move $a0 $s0
    bnez $a0 dispatch22
    la $a0 str_const7
    li $t1 129
    jal _dispatch_abort
dispatch22:
    lw $t1 8($a0) # dispatch table
    lw $t1 0($t1) # method offset
    jalr $t1
    la $a0 str_const0
endif42:
endif43:
endif44:
endif45:
endif46:
endif47:
endif48:
endif49:
endif50:
endif51:
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    addiu $sp $sp 4
    jr $ra
A2I.a2i:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    lw $a0 12($fp)
    bnez $a0 dispatch23
    la $a0 str_const7
    li $t1 142
    jal _dispatch_abort
dispatch23:
    lw $t1 8($a0) # dispatch table
    lw $t1 12($t1) # method offset
    jalr $t1
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const0
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq52
    la $a1 bool_const0
    jal equality_test
eq52:
    lw $t1 12($a0) # bool slot
    beqz $t1 else57
    la $a0 int_const0
    b endif57
else57:
    la $a0 int_const6
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const0
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 12($fp)
    bnez $a0 dispatch24
    la $a0 str_const7
    li $t1 143
    jal _dispatch_abort
dispatch24:
    lw $t1 8($a0) # dispatch table
    lw $t1 20($t1) # method offset
    jalr $t1
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 str_const26
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq53
    la $a1 bool_const0
    jal equality_test
eq53:
    lw $t1 12($a0) # bool slot
    beqz $t1 else56
    lw $a0 12($fp)
    bnez $a0 dispatch25
    la $a0 str_const7
    li $t1 143
    jal _dispatch_abort
dispatch25:
    lw $t1 8($a0) # dispatch table
    lw $t1 12($t1) # method offset
    jalr $t1
	sw $a0 0($sp)
	addiu $sp $sp -4
    la $a0 int_const6
    jal Object.copy
	lw $t1 4($sp)
	lw $t1 12($t1) # int slot
    lw $t2 12($a0) # int slot
	sub $t1 $t1 $t2
    sw $t1 12($a0) # int slot
	addiu $sp $sp 4
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const6
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 12($fp)
    bnez $a0 dispatch26
    la $a0 str_const7
    li $t1 143
    jal _dispatch_abort
dispatch26:
    lw $t1 8($a0) # dispatch table
    lw $t1 20($t1) # method offset
    jalr $t1
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch27
    la $a0 str_const7
    li $t1 143
    jal _dispatch_abort
dispatch27:
    lw $t1 8($a0) # dispatch table
    lw $t1 24($t1) # method offset
    jalr $t1
    jal Object.copy
    lw $t1 12($a0) # int slot
    neg $t1 $t1
    sw $t1 12($a0) # int slot
    b endif56
else56:
    la $a0 int_const6
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const0
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 12($fp)
    bnez $a0 dispatch28
    la $a0 str_const7
    li $t1 144
    jal _dispatch_abort
dispatch28:
    lw $t1 8($a0) # dispatch table
    lw $t1 20($t1) # method offset
    jalr $t1
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 str_const27
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq54
    la $a1 bool_const0
    jal equality_test
eq54:
    lw $t1 12($a0) # bool slot
    beqz $t1 else55
    lw $a0 12($fp)
    bnez $a0 dispatch29
    la $a0 str_const7
    li $t1 144
    jal _dispatch_abort
dispatch29:
    lw $t1 8($a0) # dispatch table
    lw $t1 12($t1) # method offset
    jalr $t1
	sw $a0 0($sp)
	addiu $sp $sp -4
    la $a0 int_const6
    jal Object.copy
	lw $t1 4($sp)
	lw $t1 12($t1) # int slot
    lw $t2 12($a0) # int slot
	sub $t1 $t1 $t2
    sw $t1 12($a0) # int slot
	addiu $sp $sp 4
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const6
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 12($fp)
    bnez $a0 dispatch30
    la $a0 str_const7
    li $t1 144
    jal _dispatch_abort
dispatch30:
    lw $t1 8($a0) # dispatch table
    lw $t1 20($t1) # method offset
    jalr $t1
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch31
    la $a0 str_const7
    li $t1 144
    jal _dispatch_abort
dispatch31:
    lw $t1 8($a0) # dispatch table
    lw $t1 24($t1) # method offset
    jalr $t1
    b endif55
else55:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch32
    la $a0 str_const7
    li $t1 145
    jal _dispatch_abort
dispatch32:
    lw $t1 8($a0) # dispatch table
    lw $t1 24($t1) # method offset
    jalr $t1
endif55:
endif56:
endif57:
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    addiu $sp $sp 4
    jr $ra
A2I.a2i_aux:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    addiu $sp $sp -4
    la $a0 int_const0
    sw $a0 -4($fp)
    addiu $sp $sp -4

    lw $a0 12($fp)
    bnez $a0 dispatch33
    la $a0 str_const7
    li $t1 156
    jal _dispatch_abort
dispatch33:
    lw $t1 8($a0) # dispatch table
    lw $t1 12($t1) # method offset
    jalr $t1
    sw $a0 -4($fp)
    addiu $sp $sp -4
    la $a0 int_const0
    sw $a0 -4($fp)
while59:
    lw $a0 -4($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 -4($fp)
    lw $t1 4($sp)
    addiu $sp $sp 4
    lw $t1 12($t1) # int slot
    lw $t2 12($a0) # int slot
    la $a0 bool_const1
    blt $t1 $t2 compare58
    la $a0 bool_const0
compare58:
    lw $t1 12($a0) # bool slot
    beqz $t1 endwhile59
    lw $a0 -4($fp)
	sw $a0 0($sp)
	addiu $sp $sp -4
    la $a0 int_const12
    jal Object.copy
	lw $t1 4($sp)
	lw $t1 12($t1) # int slot
    lw $t2 12($a0) # int slot
	mul $t1 $t1 $t2
    sw $t1 12($a0) # int slot
	addiu $sp $sp 4
	sw $a0 0($sp)
	addiu $sp $sp -4
    la $a0 int_const6
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 -4($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 12($fp)
    bnez $a0 dispatch34
    la $a0 str_const7
    li $t1 160
    jal _dispatch_abort
dispatch34:
    lw $t1 8($a0) # dispatch table
    lw $t1 20($t1) # method offset
    jalr $t1
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch35
    la $a0 str_const7
    li $t1 160
    jal _dispatch_abort
dispatch35:
    lw $t1 8($a0) # dispatch table
    lw $t1 12($t1) # method offset
    jalr $t1
    jal Object.copy
	lw $t1 4($sp)
	lw $t1 12($t1) # int slot
    lw $t2 12($a0) # int slot
	add $t1 $t1 $t2
    sw $t1 12($a0) # int slot
	addiu $sp $sp 4
    sw $a0 -4($fp)
    lw $a0 -4($fp)
	sw $a0 0($sp)
	addiu $sp $sp -4
    la $a0 int_const6
    jal Object.copy
	lw $t1 4($sp)
	lw $t1 12($t1) # int slot
    lw $t2 12($a0) # int slot
	add $t1 $t1 $t2
    sw $t1 12($a0) # int slot
	addiu $sp $sp 4
    sw $a0 -4($fp)
    b while59
endwhile59:
    move $a0 $zero
    addiu $sp $sp 4
    addiu $sp $sp 4
    lw $a0 -4($fp)
    addiu $sp $sp 4
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    addiu $sp $sp 4
    jr $ra
A2I.i2a:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const0
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq60
    la $a1 bool_const0
    jal equality_test
eq60:
    lw $t1 12($a0) # bool slot
    beqz $t1 else63
    la $a0 str_const16
    b endif63
else63:
    la $a0 int_const0
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 12($fp)
    lw $t1 4($sp)
    addiu $sp $sp 4
    lw $t1 12($t1) # int slot
    lw $t2 12($a0) # int slot
    la $a0 bool_const1
    blt $t1 $t2 compare61
    la $a0 bool_const0
compare61:
    lw $t1 12($a0) # bool slot
    beqz $t1 else62
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch36
    la $a0 str_const7
    li $t1 177
    jal _dispatch_abort
dispatch36:
    lw $t1 8($a0) # dispatch table
    lw $t1 32($t1) # method offset
    jalr $t1
    b endif62
else62:
    lw $a0 12($fp)
	sw $a0 0($sp)
	addiu $sp $sp -4
    la $a0 int_const6
    jal Object.copy
    lw $t1 12($a0) # int slot
    neg $t1 $t1
    sw $t1 12($a0) # int slot
    jal Object.copy
	lw $t1 4($sp)
	lw $t1 12($t1) # int slot
    lw $t2 12($a0) # int slot
	mul $t1 $t1 $t2
    sw $t1 12($a0) # int slot
	addiu $sp $sp 4
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch37
    la $a0 str_const7
    li $t1 178
    jal _dispatch_abort
dispatch37:
    lw $t1 8($a0) # dispatch table
    lw $t1 32($t1) # method offset
    jalr $t1
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 str_const26
    bnez $a0 dispatch38
    la $a0 str_const7
    li $t1 178
    jal _dispatch_abort
dispatch38:
    lw $t1 8($a0) # dispatch table
    lw $t1 16($t1) # method offset
    jalr $t1
endif62:
endif63:
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    addiu $sp $sp 4
    jr $ra
A2I.i2a_aux:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const0
    lw $t1 4($sp)
    addiu $sp $sp 4
    move $t2 $a0
    la $a0 bool_const1
    beq $t1 $t2 eq64
    la $a1 bool_const0
    jal equality_test
eq64:
    lw $t1 12($a0) # bool slot
    beqz $t1 else65
    la $a0 str_const0
    b endif65
else65:
    addiu $sp $sp -4
    lw $a0 12($fp)
	sw $a0 0($sp)
	addiu $sp $sp -4
    la $a0 int_const12
    jal Object.copy
	lw $t1 4($sp)
	lw $t1 12($t1) # int slot
    lw $t2 12($a0) # int slot
	div $t1 $t1 $t2
    sw $t1 12($a0) # int slot
	addiu $sp $sp 4
    sw $a0 -4($fp)
    lw $a0 12($fp)
	sw $a0 0($sp)
	addiu $sp $sp -4
    lw $a0 -4($fp)
	sw $a0 0($sp)
	addiu $sp $sp -4
    la $a0 int_const12
    jal Object.copy
	lw $t1 4($sp)
	lw $t1 12($t1) # int slot
    lw $t2 12($a0) # int slot
	mul $t1 $t1 $t2
    sw $t1 12($a0) # int slot
	addiu $sp $sp 4
    jal Object.copy
	lw $t1 4($sp)
	lw $t1 12($t1) # int slot
    lw $t2 12($a0) # int slot
	sub $t1 $t1 $t2
    sw $t1 12($a0) # int slot
	addiu $sp $sp 4
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch39
    la $a0 str_const7
    li $t1 188
    jal _dispatch_abort
dispatch39:
    lw $t1 8($a0) # dispatch table
    lw $t1 16($t1) # method offset
    jalr $t1
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 -4($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch40
    la $a0 str_const7
    li $t1 188
    jal _dispatch_abort
dispatch40:
    lw $t1 8($a0) # dispatch table
    lw $t1 32($t1) # method offset
    jalr $t1
    bnez $a0 dispatch41
    la $a0 str_const7
    li $t1 188
    jal _dispatch_abort
dispatch41:
    lw $t1 8($a0) # dispatch table
    lw $t1 16($t1) # method offset
    jalr $t1
    addiu $sp $sp 4
endif65:
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    addiu $sp $sp 4
    jr $ra
