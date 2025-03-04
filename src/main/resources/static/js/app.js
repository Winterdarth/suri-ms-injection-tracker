document.addEventListener('DOMContentLoaded', async function() {
    const currentDate = document.getElementById('current-date');
    const currentBodyPart = document.getElementById('current-body-part');
    const nextDate = document.getElementById('next-date');
    const nextBodyPart = document.getElementById('next-body-part');
    const markDoneBtn = document.getElementById('mark-done-btn');
    function formatBodyPart(bodyPart) {
        return bodyPart
            .replace(/_/g, ' ')
            .toLowerCase()
            .replace(/\b\w/g, (char) => char.toUpperCase());
    }

    async function fetchInjectionSchedule() {
        try {
            const response = await fetch('/api/schedules');
            if (!response.ok) {
                throw new Error('Failed to fetch injection schedule data');
            }
            const data = await response.json();

            return data.map(item => ({
                ...item,
                bodyPart: formatBodyPart(item.bodyPart)
            }));
        } catch (error) {
            console.error('Error fetching injection schedule data:', error);
            return [];
        }
    }

    function renderSchedule(scheduleData) {
        if (scheduleData.length >= 2) {
            currentDate.textContent = scheduleData[0].injectionDate;
            currentBodyPart.textContent = scheduleData[0].bodyPart;
            nextDate.textContent = scheduleData[1].injectionDate;
            nextBodyPart.textContent = scheduleData[1].bodyPart;
        } else if (scheduleData.length === 1) {
            const injection = scheduleData[0];
            currentDate.textContent = injection.injectionDate;
            currentBodyPart.textContent = injection.bodyPart;
            nextDate.textContent = injection.injectionDate;
            nextBodyPart.textContent = injection.bodyPart;
        } else {
            console.error('No injection schedule data found');
        }

        const today = new Date().toISOString().split('T')[0];
        const isToday = currentDate.textContent === today;

        if (isToday) {
            markDoneBtn.disabled = false;
            markDoneBtn.textContent = "Beadtam";
            markDoneBtn.classList.remove('used');
        } else {
            markDoneBtn.disabled = true;
            markDoneBtn.textContent = "Majd";
            markDoneBtn.classList.add('used');
        }
    }

    async function markDone() {
        try {
            const scheduleData = await fetchInjectionSchedule();
            if (scheduleData.length === 0) {
                console.error('No injection schedule data found');
                return;
            }
            const currentScheduleId = scheduleData[0].id;
            const response = await fetch(`/api/schedules/${currentScheduleId}/complete`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ id: currentScheduleId })
            });
            if (!response.ok) {
                throw new Error('Failed to mark injection as done');
            }
            await renderSchedule(scheduleData);

            markDoneBtn.disabled = true;
            markDoneBtn.textContent = 'Már Beadva';
            markDoneBtn.classList.add('used');
        } catch (error) {
            console.error('Error marking injection as done:', error);
        }
    }

    if (markDoneBtn) {
        markDoneBtn.addEventListener('click', markDone);
    } else {
        console.error('mark-done-btn not found');
    }

    const scheduleData = await fetchInjectionSchedule();
    renderSchedule(scheduleData);
});
