async function analyzeSkills() {
  const userName = document.getElementById("userName").value.trim();
  const skills = document.getElementById("skills").value.trim();
  const roleId = document.getElementById("roleId").value;

  console.log("userName:", userName);
  console.log("skills:", skills);
  console.log("roleId:", roleId);

  if (!userName || !skills) {
    alert("Please fill in your name and skills!");
    return;
  }

  document.getElementById("input-section").classList.add("hidden");
  document.getElementById("loader").classList.remove("hidden");
  document.getElementById("results").classList.add("hidden");

  try {
    const body = "userName=" + encodeURIComponent(userName) +
                 "&skills=" + encodeURIComponent(skills) +
                 "&roleId=" + encodeURIComponent(roleId);

    console.log("Sending body:", body);

    const response = await fetch("/skillgap/analyze", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
      },
      body: body
    });

    const text = await response.text();
    console.log("Raw response:", text);
    const data = JSON.parse(text);

    if (data.error) throw new Error(data.error);
    displayResults(data);

  } catch (err) {
    alert("Error: " + err.message);
    document.getElementById("input-section").classList.remove("hidden");
  }

  document.getElementById("loader").classList.add("hidden");
}

function displayResults(data) {
  document.getElementById("result-title").textContent =
    data.userName + " → " + data.roleName;

  document.getElementById("score-number").textContent = data.score + "%";

  let label = "Beginner level — keep learning! 💪";
  if (data.score >= 70) label = "Strong match! 🎉";
  else if (data.score >= 40) label = "Intermediate level 📈";
  document.getElementById("score-label").textContent = label;

  const matchedList = document.getElementById("matched-list");
  matchedList.innerHTML = data.matched.length
    ? data.matched.map(s => "<li>✅ " + s + "</li>").join("")
    : "<li>No matches yet</li>";

  const missingList = document.getElementById("missing-list");
  missingList.innerHTML = data.missing.length
    ? data.missing.map(s => "<li>❌ " + s + "</li>").join("")
    : "<li>All skills matched! 🎉</li>";

  const roadmap = document.getElementById("roadmap-list");
  roadmap.innerHTML = data.missingEssential.length
    ? data.missingEssential.map((s, i) =>
        "<div class='roadmap-item'>" +
        "<div class='step-num'>" + (i + 1) + "</div>" +
        "<div>" +
        "<div class='skill-name'>" + s + "</div>" +
        "<div class='skill-time'>⏱ Estimated: 3-4 weeks</div>" +
        "</div></div>"
      ).join("")
    : "<p style='color:#4ade80'>You have all essential skills! 🎉</p>";

  document.getElementById("results").classList.remove("hidden");
}

function resetForm() {
  document.getElementById("results").classList.add("hidden");
  document.getElementById("input-section").classList.remove("hidden");
  document.getElementById("userName").value = "";
  document.getElementById("skills").value = "";
}