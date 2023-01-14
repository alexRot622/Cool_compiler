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
.globl Main_protObj
.globl A2I_protObj

_int_tag:
    .word 2
_string_tag:
    .word 3
_bool_tag:
    .word 4

int_const0:
    .word 2
    .word 4
    .word Int_dispTab
    .word 0
str_const0:
    .word 3
    .word 4
    .word String_dispTab
    .word int_const0
    .asciiz ""
    .align 2
bool_const0:
    .word 4
    .word 4
    .word Bool_dispTab
    .word 0
bool_const1:
    .word 4
    .word 4
    .word Bool_dispTab
    .word 1
int_const1:
    .word 2
    .word 4
    .word Int_dispTab
    .word 6
str_const1:
    .word 3
    .word 6
    .word String_dispTab
    .word int_const1
    .asciiz "Object"
    .align 2
int_const2:
    .word 2
    .word 4
    .word Int_dispTab
    .word 2
str_const2:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const2
    .asciiz "IO"
    .align 2
int_const3:
    .word 2
    .word 4
    .word Int_dispTab
    .word 3
str_const3:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const3
    .asciiz "Int"
    .align 2
str_const4:
    .word 3
    .word 6
    .word String_dispTab
    .word int_const1
    .asciiz "String"
    .align 2
int_const4:
    .word 2
    .word 4
    .word Int_dispTab
    .word 4
str_const5:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const4
    .asciiz "Bool"
    .align 2
str_const6:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const4
    .asciiz "List"
    .align 2
int_const5:
    .word 2
    .word 4
    .word Int_dispTab
    .word 1
str_const7:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const5
    .asciiz " "
    .align 2
int_const6:
    .word 2
    .word 4
    .word Int_dispTab
    .word 9
str_const8:
    .word 3
    .word 7
    .word String_dispTab
    .word int_const6
    .asciiz "32-big.cl"
    .align 2
str_const9:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const5
    .asciiz "
"
    .align 2
str_const10:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const4
    .asciiz "Main"
    .align 2
str_const11:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const5
    .asciiz "!"
    .align 2
int_const7:
    .word 2
    .word 4
    .word Int_dispTab
    .word 27
str_const12:
    .word 3
    .word 11
    .word String_dispTab
    .word int_const7
    .asciiz "Calculam factorial pentru: "
    .align 2
int_const8:
    .word 2
    .word 4
    .word Int_dispTab
    .word 20
str_const13:
    .word 3
    .word 9
    .word String_dispTab
    .word int_const8
    .asciiz "Factorial recursiv: "
    .align 2
str_const14:
    .word 3
    .word 9
    .word String_dispTab
    .word int_const8
    .asciiz "Factorial iterativ: "
    .align 2
str_const15:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const3
    .asciiz "A2I"
    .align 2
int_const9:
    .word 2
    .word 4
    .word Int_dispTab
    .word 5
int_const10:
    .word 2
    .word 4
    .word Int_dispTab
    .word 7
int_const11:
    .word 2
    .word 4
    .word Int_dispTab
    .word 8
str_const16:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const5
    .asciiz "0"
    .align 2
str_const17:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const5
    .asciiz "1"
    .align 2
str_const18:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const5
    .asciiz "2"
    .align 2
str_const19:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const5
    .asciiz "3"
    .align 2
str_const20:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const5
    .asciiz "4"
    .align 2
str_const21:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const5
    .asciiz "5"
    .align 2
str_const22:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const5
    .asciiz "6"
    .align 2
str_const23:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const5
    .asciiz "7"
    .align 2
str_const24:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const5
    .asciiz "8"
    .align 2
str_const25:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const5
    .asciiz "9"
    .align 2
str_const26:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const5
    .asciiz "-"
    .align 2

class_nameTab:
    .word str_const1
    .word str_const2
    .word str_const3
    .word str_const4
    .word str_const5
    .word str_const6
    .word str_const10
    .word str_const15

class_objTab:
    .word Object_protObj
    .word Object_init
    .word IO_protObj
    .word IO_init
    .word Int_protObj
    .word Int_init
    .word String_protObj
    .word String_init
    .word Bool_protObj
    .word Bool_init
    .word List_protObj
    .word List_init
    .word Main_protObj
    .word Main_init
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
    .word 2
    .word 4
    .word Int_dispTab
    .word 0
String_protObj:
    .word 3
    .word 5
    .word String_dispTab
    .word int_const0
    .asciiz ""
Bool_protObj:
    .word 4
    .word 4
    .word Bool_dispTab
    .word bool_const0
List_protObj:
    .word 5
    .word 5
    .word List_dispTab
    .word 0
    .word 0
Main_protObj:
    .word 6
    .word 3
    .word Main_dispTab
A2I_protObj:
    .word 7
    .word 3
    .word A2I_dispTab

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
List.print:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    addiu $sp $sp -4
    sw $a0 -4($fp)
    la $a0 str_const7
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 -4($fp)
    bnez $a0 dispatch0
    la $a0 str_const8
    li $t1 31
    jal _dispatch_abort
dispatch0:
    lw $t1 8($a0) # dispatch table
    lw $t1 16($t1) # method offset
    jalr $t1
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch1
    la $a0 str_const8
    li $t1 31
    jal _dispatch_abort
dispatch1:
    lw $t1 8($a0) # dispatch table
    lw $t1 12($t1) # method offset
    jalr $t1

    lw $t1 12($a0) # bool slot
    beqz $t1 else0
    la $a0 str_const9
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch2
    la $a0 str_const8
    li $t1 32
    jal _dispatch_abort
dispatch2:
    lw $t1 8($a0) # dispatch table
    lw $t1 12($t1) # method offset
    jalr $t1
    b endif0
else0:
    lw $a0 16($s0)
    bnez $a0 dispatch3
    la $a0 str_const8
    li $t1 32
    jal _dispatch_abort
dispatch3:
    lw $t1 8($a0) # dispatch table
    lw $t1 32($t1) # method offset
    jalr $t1
endif0:
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
    la $a0 str_const11
    sw $a0 -8($fp)
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
    bnez $a0 dispatch4
    la $a0 str_const8
    li $t1 47
    jal _dispatch_abort
dispatch4:
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
    bnez $a0 dispatch5
    la $a0 str_const8
    li $t1 46
    jal _dispatch_abort
dispatch5:
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
    bnez $a0 dispatch6
    la $a0 str_const8
    li $t1 45
    jal _dispatch_abort
dispatch6:
    lw $t1 8($a0) # dispatch table
    lw $t1 28($t1) # method offset
    jalr $t1
    sw $a0 -20($fp)

    lw $a0 -20($fp)
    bnez $a0 dispatch7
    la $a0 str_const8
    li $t1 49
    jal _dispatch_abort
dispatch7:
    lw $t1 8($a0) # dispatch table
    lw $t1 32($t1) # method offset
    jalr $t1
    addiu $sp $sp 20
    addiu $sp $sp -4

    la $a0 str_const12
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch8
    la $a0 str_const8
    li $t1 52
    jal _dispatch_abort
dispatch8:
    lw $t1 8($a0) # dispatch table
    lw $t1 12($t1) # method offset
    jalr $t1
    bnez $a0 dispatch9
    la $a0 str_const8
    li $t1 52
    jal _dispatch_abort
dispatch9:
    lw $t1 8($a0) # dispatch table
    lw $t1 24($t1) # method offset
    jalr $t1
    sw $a0 -4($fp)
    la $a0 str_const9
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 -4($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch10
    la $a0 str_const8
    li $t1 55
    jal _dispatch_abort
dispatch10:
    lw $t1 8($a0) # dispatch table
    lw $t1 32($t1) # method offset
    jalr $t1
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 str_const13
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch11
    la $a0 str_const8
    li $t1 55
    jal _dispatch_abort
dispatch11:
    lw $t1 8($a0) # dispatch table
    lw $t1 12($t1) # method offset
    jalr $t1
    bnez $a0 dispatch12
    la $a0 str_const8
    li $t1 55
    jal _dispatch_abort
dispatch12:
    lw $t1 8($a0) # dispatch table
    lw $t1 16($t1) # method offset
    jalr $t1
    bnez $a0 dispatch13
    la $a0 str_const8
    li $t1 55
    jal _dispatch_abort
dispatch13:
    lw $t1 8($a0) # dispatch table
    lw $t1 12($t1) # method offset
    jalr $t1
    la $a0 str_const9
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 -4($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch14
    la $a0 str_const8
    li $t1 57
    jal _dispatch_abort
dispatch14:
    lw $t1 8($a0) # dispatch table
    lw $t1 36($t1) # method offset
    jalr $t1
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 str_const14
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch15
    la $a0 str_const8
    li $t1 57
    jal _dispatch_abort
dispatch15:
    lw $t1 8($a0) # dispatch table
    lw $t1 12($t1) # method offset
    jalr $t1
    bnez $a0 dispatch16
    la $a0 str_const8
    li $t1 57
    jal _dispatch_abort
dispatch16:
    lw $t1 8($a0) # dispatch table
    lw $t1 16($t1) # method offset
    jalr $t1
    bnez $a0 dispatch17
    la $a0 str_const8
    li $t1 57
    jal _dispatch_abort
dispatch17:
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
    lw $t1 12($a0) # bool slot
    beqz $t1 else1
    la $a0 int_const5
    b endif1
else1:
endif1:
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
    la $a0 int_const5
    sw $a0 -4($fp)
    lw $a0 -4($fp)
    addiu $sp $sp 4
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    addiu $sp $sp 4
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
A2I.c2i:
    addiu $sp $sp -12
    sw $fp 12($sp)
    sw $s0 8($sp)
    sw $ra 4($sp)
    addiu $fp $sp 4
    move $s0 $a0
    lw $t1 12($a0) # bool slot
    beqz $t1 else11
    la $a0 int_const0
    b endif11
else11:
    lw $t1 12($a0) # bool slot
    beqz $t1 else10
    la $a0 int_const5
    b endif10
else10:
    lw $t1 12($a0) # bool slot
    beqz $t1 else9
    la $a0 int_const2
    b endif9
else9:
    lw $t1 12($a0) # bool slot
    beqz $t1 else8
    la $a0 int_const3
    b endif8
else8:
    lw $t1 12($a0) # bool slot
    beqz $t1 else7
    la $a0 int_const4
    b endif7
else7:
    lw $t1 12($a0) # bool slot
    beqz $t1 else6
    la $a0 int_const9
    b endif6
else6:
    lw $t1 12($a0) # bool slot
    beqz $t1 else5
    la $a0 int_const1
    b endif5
else5:
    lw $t1 12($a0) # bool slot
    beqz $t1 else4
    la $a0 int_const10
    b endif4
else4:
    lw $t1 12($a0) # bool slot
    beqz $t1 else3
    la $a0 int_const11
    b endif3
else3:
    lw $t1 12($a0) # bool slot
    beqz $t1 else2
    la $a0 int_const6
    b endif2
else2:
	move $a0 $s0
    bnez $a0 dispatch18
    la $a0 str_const8
    li $t1 111
    jal _dispatch_abort
dispatch18:
    lw $t1 8($a0) # dispatch table
    lw $t1 0($t1) # method offset
    jalr $t1
    la $a0 int_const0
endif2:
endif3:
endif4:
endif5:
endif6:
endif7:
endif8:
endif9:
endif10:
endif11:
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
    lw $t1 12($a0) # bool slot
    beqz $t1 else21
    la $a0 str_const16
    b endif21
else21:
    lw $t1 12($a0) # bool slot
    beqz $t1 else20
    la $a0 str_const17
    b endif20
else20:
    lw $t1 12($a0) # bool slot
    beqz $t1 else19
    la $a0 str_const18
    b endif19
else19:
    lw $t1 12($a0) # bool slot
    beqz $t1 else18
    la $a0 str_const19
    b endif18
else18:
    lw $t1 12($a0) # bool slot
    beqz $t1 else17
    la $a0 str_const20
    b endif17
else17:
    lw $t1 12($a0) # bool slot
    beqz $t1 else16
    la $a0 str_const21
    b endif16
else16:
    lw $t1 12($a0) # bool slot
    beqz $t1 else15
    la $a0 str_const22
    b endif15
else15:
    lw $t1 12($a0) # bool slot
    beqz $t1 else14
    la $a0 str_const23
    b endif14
else14:
    lw $t1 12($a0) # bool slot
    beqz $t1 else13
    la $a0 str_const24
    b endif13
else13:
    lw $t1 12($a0) # bool slot
    beqz $t1 else12
    la $a0 str_const25
    b endif12
else12:
	move $a0 $s0
    bnez $a0 dispatch19
    la $a0 str_const8
    li $t1 129
    jal _dispatch_abort
dispatch19:
    lw $t1 8($a0) # dispatch table
    lw $t1 0($t1) # method offset
    jalr $t1
    la $a0 str_const0
endif12:
endif13:
endif14:
endif15:
endif16:
endif17:
endif18:
endif19:
endif20:
endif21:
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
    lw $t1 12($a0) # bool slot
    beqz $t1 else24
    la $a0 int_const0
    b endif24
else24:
    lw $t1 12($a0) # bool slot
    beqz $t1 else23
    b endif23
else23:
    lw $t1 12($a0) # bool slot
    beqz $t1 else22
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 int_const5
    sw $a0 0($sp)
    addiu $sp $sp -4
    lw $a0 12($fp)
    bnez $a0 dispatch20
    la $a0 str_const8
    li $t1 144
    jal _dispatch_abort
dispatch20:
    lw $t1 8($a0) # dispatch table
    lw $t1 20($t1) # method offset
    jalr $t1
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch21
    la $a0 str_const8
    li $t1 144
    jal _dispatch_abort
dispatch21:
    lw $t1 8($a0) # dispatch table
    lw $t1 24($t1) # method offset
    jalr $t1
    b endif22
else22:
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch22
    la $a0 str_const8
    li $t1 145
    jal _dispatch_abort
dispatch22:
    lw $t1 8($a0) # dispatch table
    lw $t1 24($t1) # method offset
    jalr $t1
endif22:
endif23:
endif24:
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
    lw $t1 12($a0) # bool slot
    beqz $t1 else26
    la $a0 str_const16
    b endif26
else26:
    lw $t1 12($a0) # bool slot
    beqz $t1 else25
    lw $a0 12($fp)
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch23
    la $a0 str_const8
    li $t1 177
    jal _dispatch_abort
dispatch23:
    lw $t1 8($a0) # dispatch table
    lw $t1 32($t1) # method offset
    jalr $t1
    b endif25
else25:
    sw $a0 0($sp)
    addiu $sp $sp -4
	move $a0 $s0
    bnez $a0 dispatch24
    la $a0 str_const8
    li $t1 178
    jal _dispatch_abort
dispatch24:
    lw $t1 8($a0) # dispatch table
    lw $t1 32($t1) # method offset
    jalr $t1
    sw $a0 0($sp)
    addiu $sp $sp -4
    la $a0 str_const26
    bnez $a0 dispatch25
    la $a0 str_const8
    li $t1 178
    jal _dispatch_abort
dispatch25:
    lw $t1 8($a0) # dispatch table
    lw $t1 16($t1) # method offset
    jalr $t1
endif25:
endif26:
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
    lw $t1 12($a0) # bool slot
    beqz $t1 else27
    la $a0 str_const0
    b endif27
else27:
endif27:
    lw $fp 12($sp)
    lw $s0 8($sp)
    lw $ra 4($sp)
    addiu $sp $sp 12
    addiu $sp $sp 4
    jr $ra
