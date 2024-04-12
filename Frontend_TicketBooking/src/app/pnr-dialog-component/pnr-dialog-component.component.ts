import { Component } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Inject, OnInit } from '@angular/core';
@Component({
  selector: 'app-pnr-dialog-component',
  templateUrl: './pnr-dialog-component.component.html',
  styleUrl: './pnr-dialog-component.component.css'
})
export class PnrDialogComponentComponent {
  constructor(
    public dialogRef: MatDialogRef<PnrDialogComponentComponent>,
    @Inject(MAT_DIALOG_DATA) public pnr: String
  ) {}

  closeDialog(): void {
    this.dialogRef.close();
  }
}
