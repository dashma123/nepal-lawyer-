/**
 * for rescheduleModal which is used in Reschedule Popup for Appointment
 */

let currentRow = null;

function openRescheduleModal(button) {
  currentRow = button.closest("tr");
  const appointmentId = currentRow.querySelector("td:nth-child(2)").textContent.trim();
  document.getElementById("rescheduleAppointmentId").value = appointmentId;

  const dateText = currentRow.querySelector(".date-cell").textContent.trim();
  // convert dd-mm-yyyy to yyyy-mm-dd for input type=date
  const parts = dateText.split("-");
  const formattedDate = `${parts[2]}-${parts[1]}-${parts[0]}`;
  document.getElementById("newDate").value = formattedDate;

  document.getElementById("selectedTime").value = "";
  document.querySelectorAll(".time-slot-btn").forEach(btn => btn.classList.remove("active"));

  document.getElementById("rescheduleModal").style.display = "flex";
}

function closeRescheduleModal() {
  document.getElementById("rescheduleModal").style.display = "none";
  currentRow = null;
}

// Time slot button click handling
document.querySelectorAll(".time-slot-btn").forEach(btn => {
  btn.addEventListener("click", () => {
    document.querySelectorAll(".time-slot-btn").forEach(b => b.classList.remove("active"));
    btn.classList.add("active");
    document.getElementById("selectedTime").value = btn.dataset.time;
  });
});

// Optional: validate time slot selected before submit
document.getElementById("rescheduleForm").addEventListener("submit", function(e) {
  if (!document.getElementById("selectedTime").value) {
    e.preventDefault();
    alert("Please select a time slot.");
  }
});
