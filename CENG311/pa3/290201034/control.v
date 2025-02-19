module control (input [7:0] in, output regdest, output alusrc, output memtoreg, output regwrite, output memread, output memwrite,
                output branch, output aluop0, output aluop1, output jump, output bne, output jalfor_jump);

  wire rformat, lw, sw, beq, jump1, bne1, addi, jalfor;

  // Assigns according to the my student ID: 290201034
  assign rformat = (~in[7]) & (~in[6]) & in[5] & (~in[4]) & (~in[3]) & (~in[2]) & in[1] & (~in[0]);   // R-Type: 00100010
  assign lw = (~in[7]) & (~in[6]) & in[5] & (~in[4]) & (~in[3]) & (~in[2]) & in[1] & in[0];           // lw:     00100011
  assign sw = (~in[7]) & (~in[6]) & in[5] & (~in[4]) & (~in[3]) & in[2] & (~in[1]) & (~in[0]);        // sw:     00100100
  assign beq = (~in[7]) & (~in[6]) & in[5] & (~in[4]) & (~in[3]) & in[2] & (~in[1]) & in[0];          // beq:    00100101
  assign bne1 = (~in[7]) & (~in[6]) & in[5] & (~in[4]) & (~in[3]) & in[2] & in[1] & (~in[0]);         // bne:    00100110
  assign addi = (~in[7]) & (~in[6]) & in[5] & (~in[4]) & (~in[3]) & in[2] & in[1] & in[0];            // addi:   00100111
  assign jump1 = (~in[7]) & (~in[6]) & in[5] & (~in[4]) & in[3] & (~in[2]) & (~in[1]) & (~in[0]);     // j:      00101000
  assign jalfor = (~in[7]) & (~in[6]) & in[5] & (~in[4]) & in[3] & (~in[2]) & (~in[1]) & in[0];       // jalfor: 00101001

  // Control signals
  assign regdest = rformat;
  assign alusrc = lw | sw | addi;
  assign memtoreg = lw;
  assign regwrite = rformat | lw | addi;
  assign memread = lw;
  assign memwrite = sw;
  assign branch = beq | bne1;
  assign aluop0 = rformat;
  assign aluop1 = beq | bne1;
  assign jump = jump1 | jalfor;
  assign bne = bne1;
  assign jalfor_jump = jalfor;

endmodule
