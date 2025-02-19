module processor;

  reg clk;
  reg [31:0] pc;
  reg [7:0] datmem[0:63], mem[0:31];
  reg [31:0] jalfor_reg[0:5];

//////////////////////////////////////////////
//                  Wires

  wire [31:0] dataa, datab;
  wire [31:0] out2, out3, out4, out5, out6;
  wire [31:0] sum, extad, adder1out, adder2out, sextad, readdata, jumpaddress;
  wire [23:0] inst23_0;
  wire [7:0] inst31_24;
  wire [3:0] inst23_20, inst19_16, inst15_12, out1;
  wire [5:0] shamt;
  wire [15:0] inst15_0;
  wire [31:0] instruc, dpack;
  wire [31:0] jump_ext;
  wire [2:0] gout;

  wire cout, zout, nout, pcsrc, bnesrc, beqsrc, regdest, alusrc, memtoreg, regwrite, memread,
      memwrite, branch, aluop1, aluop0, jump, jalfor;



//////////////////////////////////////////////
//              Register File

  reg [31:0] registerfile[0:15];
  integer i, c;
  reg [31:0] t_mem;



//////////////////////////////////////////////
//      When memwrite is active, write

  always @(posedge clk) begin
    if (memwrite) begin
      datmem[sum[5:0] + 3] <= datab[7:0];
      datmem[sum[5:0] + 2] <= datab[15:8];
      datmem[sum[5:0] + 1] <= datab[23:16];
      datmem[sum[5:0]] <= datab[31:24];
    end
  end



//////////////////////////////////////////////
//           Instruction Memory

  assign instruc = {mem[pc[4:0]],
                    mem[pc[4:0] + 1],
                    mem[pc[4:0] + 2],
                    mem[pc[4:0] + 3]};



//////////////////////////////////////////////
//   Instruction Naming (opcode, rs, rt...)

  assign inst31_24 = instruc[31:24];
  assign inst23_20 = instruc[23:20];
  assign inst19_16 = instruc[19:16];
  assign inst15_12 = instruc[15:12];
  assign shamt = instruc[11:6];
  assign func = instruc[5:0];
  assign inst15_0 = instruc[15:0];
  assign inst23_0 = instruc[23:0];

//////////////////////////////////////////////
//   dataa and datab for reading registers

  assign dataa = registerfile[inst23_20];
  assign datab = registerfile[inst19_16];



//////////////////////////////////////////////
//          dpack for memory read

  assign dpack = {datmem[sum[5:0]], datmem[sum[5:0] + 1], datmem[sum[5:0] + 2], datmem[sum[5:0] + 3]};



//////////////////////////////////////////////
//          jumpaddress for jalfor

  assign jumpaddress = jalfor ? {16'b0, instruc[15:0]} : {8'b0, instruc[23:0]};



//////////////////////////////////////////////
//  Multiplexers (mult2_to_1_1 new added.)

  mult2_to_1_5 mult1 (out1, instruc[19:16], instruc[15:12], regdest);
  mult2_to_1_32 mult2 (out2, datab, extad, alusrc);
  mult2_to_1_32 mult3 (out3, sum, dpack, memtoreg);
  mult2_to_1_32 mult4 (out4, adder1out, adder2out, pcsrc);
  mult2_to_1_32 mult5 (out5, out4, jumpaddress, jump);
  mult2_to_1_1 mult6 (pcsrc, bnesrc, beqsrc, instruc[24]);



//////////////////////////////////////////////
//        Jalfor (number of cycles)

  always @(posedge clk) begin
    if (jalfor) begin
      jalfor_reg[0] <= 1;
      jalfor_reg[1] <= (inst23_20 - 1);
      jalfor_reg[2] <= (inst19_16 - 1);
      jalfor_reg[3] <= (inst19_16 - 1);
      jalfor_reg[4] <= jumpaddress;
      jalfor_reg[5] <= pc + 4;
    end
  end



//////////////////////////////////////////////
//            Jalfor and update pc

  always @(posedge clk) begin
    if (jalfor_reg[0]) begin
      // if jalfor active
      if (jalfor_reg[3] > 0) begin
        pc <= out5;
        jalfor_reg[3] <= jalfor_reg[3] - 1;
      end else if (jalfor_reg[1] > 0) begin
        jalfor_reg[1] <= jalfor_reg[1] - 1;
        jalfor_reg[3] <= jalfor_reg[2];
        pc <= jalfor_reg[4];
      end else begin
        pc <= jalfor_reg[5];
        jalfor_reg[0] <= 0;
      end
    end else begin
      // if jalfor not active
      pc <= out5;
    end
  end



//////////////////////////////////////////////
//          Writing to registers

  always @(posedge clk) begin
    if (regwrite) registerfile[out1] <= out3;
  end



//////////////////////////////////////////////
//               ALU, Adder

  alu32 alu1 (sum, dataa, out2, zout, shamt, gout);

  adder add1 (pc, 32'h4, adder1out);

  adder add2 (adder1out, sextad, adder2out);



//////////////////////////////////////////////
//              Control Unit
  control cont (
      .in(instruc[31:24]),
      .regdest(regdest),
      .alusrc(alusrc),
      .memtoreg(memtoreg),
      .regwrite(regwrite),
      .memread(memread),
      .memwrite(memwrite),
      .branch(branch),
      .aluop0(aluop0),
      .aluop1(aluop1),
      .jump(jump),
      .bne(bne),
      .jalfor_jump(jalfor)
  );



//////////////////////////////////////////////
//              Sign Extend

  signext sext (
      .in1(inst15_0),
      .out1(extad)
  );



//////////////////////////////////////////////
//              ALU Control

  alucont acont (
      .aluop0(aluop0),
      .aluop1(aluop1),
      .f3(instruc[3]),
      .f2(instruc[2]),
      .f1(instruc[1]),
      .f0(instruc[0]),
      .gout(gout)
  );



//////////////////////////////////////////////
//             Shift Operation
  shift shift2 (
      .shin(extad),
      .shout(sextad)
  );



//////////////////////////////////////////////
//              PC Source

  assign beqsrc = branch & zout;
  assign bnesrc = bne & ~zout;



//////////////////////////////////////////////
//              Initializations

  initial
begin
	$readmemh("/home/ahmetkurtms/Desktop/iztech/ceng3_1/CENG311/homeworks/290201034/initDataMemory.dat",datmem);
	$readmemh("/home/ahmetkurtms/Desktop/iztech/ceng3_1/CENG311/homeworks/290201034/InitIM3.dat",mem);
	$readmemh("/home/ahmetkurtms/Desktop/iztech/ceng3_1/CENG311/homeworks/290201034/initRegisterMemory.dat",registerfile);


	//for(i=0; i<31; i=i+1)
	//$display("Instruction Memory[%0d]= %h  ",i,mem[i],"Data Memory[%0d]= %h   ",i,datmem[i],
	//"Register[%0d]= %h",i,registerfile[i]);

	for(i=0; i<31; i=i+1)
	$display("Instruction Memory[%0d]= %h  ",i,mem[i],"Data Memory[%0d]= 0x%h   ",i,datmem[i],
	"Register[%0d]= 0x%h ",i,registerfile[i]);
    	c = 0;
    	t_mem = 0;

    	for (i = 0; i < 31; i = i + 1) begin
      	t_mem = {t_mem[23:0], mem[i]};
      	c = c + 1;
	      	if (c == 4) begin
			c = 0;
			$display("Instruction Memory[%0d]= 0x%h [%b %b %b %b %b %b]",
		          i - 3, t_mem,
		          t_mem[31:24], t_mem[23:20],
		          t_mem[19:16], t_mem[15:12],
		          t_mem[11:6], t_mem[5:0]);
			t_mem = 0;
	      	end
    	end

end

initial
begin
	pc=0;
	#1000 $finish;
end

initial
begin
	clk=0;
forever #20  clk=~clk;
end

initial
begin
	//$monitor($time,"PC %h",pc,"  SUM %h",sum,"   INST %h",instruc[31:0],
	//"   REGISTER %h %h %h %h ",registerfile[4],registerfile[5], registerfile[6],registerfile[1] );
	$monitor($time," PC %h [%d]",pc,pc,"  SUM %h",sum,"   INST %h [%b %b %b %b %b %b]",instruc[31:0], inst31_24,inst23_20,inst19_16,inst15_12, shamt, func,
	"   REGISTER %h %h %h %h %p DATA MEMORY %p",registerfile[4],registerfile[5], registerfile[6],registerfile[1], registerfile,datmem );


end

endmodule
